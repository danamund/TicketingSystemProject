package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.controller.TicketController;
import com.hit.dm.Ticket;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class HandleRequest implements Runnable {
    private Socket socket;
    private TicketController ticketController;


    public HandleRequest(Socket socket,TicketController ticketController) {
        this.socket = socket;
        this.ticketController=ticketController;
    }

    @Override
    public void run() {
        try (Scanner reader = new Scanner(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            if (reader.hasNextLine()) {
                String jsonRequest = reader.nextLine();
                Gson gson = new Gson();


                TypeToken<Request<Ticket>> typeToken = new TypeToken<Request<Ticket>>(){};
                Request<Ticket> request = gson.fromJson(jsonRequest, typeToken.getType());

                String action = request.getHeaders().get("action");

                if ("search".equals(action)) {

                    String query = request.getBody().getEventName();

                    Ticket result = ticketController.searchTicket(query);

                    Response<Ticket> response = new Response<>(result != null ? "success" : "failed", result);
                    writer.println(gson.toJson(response));
                    writer.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}