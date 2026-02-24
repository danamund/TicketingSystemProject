package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.controller.ControllerFactory;
import com.hit.controller.TicketController;
import com.hit.dm.Ticket;
import com.hit.server.Request;
import com.hit.server.Response;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class HandleRequest implements Runnable {
    private Socket socket;
    private ControllerFactory factory;

    public HandleRequest(Socket socket, ControllerFactory factory) {
        this.socket = socket;
        this.factory = factory;
    }

    @Override
    public void run() {
        try (Scanner reader = new Scanner(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            if (reader.hasNextLine()) {
                String jsonRequest = reader.nextLine();
                // הפיכת ה-JSON לאובייקט בקשה
                TypeToken<Request<Ticket>> typeToken = new TypeToken<Request<Ticket>>(){};
                Request<Ticket> request = new Gson().fromJson(jsonRequest, typeToken.getType());

                String action = request.getHeaders().get("action");
                // קבלת הקונטרולר מה-Factory
                TicketController controller = (TicketController) factory.getController("ticket");

                Response<Ticket> response;
                if ("search".equals(action)) {
                    // ביצוע החיפוש בשרת
                    Ticket result = controller.getTicket(request.getBody().getEventName());
                    response = new Response<>("success", result);
                } else {
                    response = new Response<>("error", null);
                }

                // שליחת התשובה בחזרה ללקוח - זה החלק הכי חשוב!
                writer.println(new Gson().toJson(response));
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}