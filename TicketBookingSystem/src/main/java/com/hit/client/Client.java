package com.hit.client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.server.Request;
import com.hit.server.Response;
import com.hit.dm.Ticket;
import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public void sendRequest() {

        try (Socket socket = new Socket("127.0.0.1", 34567);

             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
             Scanner reader = new Scanner(new InputStreamReader(socket.getInputStream()))) {

            System.out.println("Connected to server...");


            Map<String, String> headers = new HashMap<>();
            headers.put("action", "search");


            Ticket searchTicket = new Ticket();
            searchTicket.setEventName("Matrx");

            Request<Ticket> request = new Request<>(headers, searchTicket);


            Gson gson = new Gson();
            String jsonRequest = gson.toJson(request);
            writer.println(jsonRequest);
            writer.flush();


            if (reader.hasNextLine()) {
                String jsonResponse = reader.nextLine();
                TypeToken<Response<Ticket>> typeToken = new TypeToken<Response<Ticket>>(){};
                Response<Ticket> response = gson.fromJson(jsonResponse, typeToken.getType());


                if ("success".equals(response.getStatus())) {
                    System.out.println("Result found: " + response.getBody().getEventName());
                } else {
                    System.out.println("No match found.");
                }
            }

        } catch (IOException e) {
            System.err.println("Client error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Client().sendRequest();
    }
}