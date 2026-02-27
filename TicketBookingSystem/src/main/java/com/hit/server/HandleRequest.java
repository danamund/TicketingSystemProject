package com.hit.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hit.controller.ControllerFactory;
import com.hit.controller.TicketController;
import com.hit.dm.Ticket;

import java.io.*;
import java.net.Socket;
import java.util.*;

/*
Submitted by:
Dana Mund, ID-319126074
Loren Kricheli ID-322632183

*/

public class HandleRequest implements Runnable {
    private final Socket socket;
    private final ControllerFactory factory;


    private static final Map<String, Set<String>> takenSeatsByShow = new HashMap<>();

    public HandleRequest(Socket socket, ControllerFactory factory) {
        this.socket = socket;
        this.factory = factory;
    }

    @Override
    public void run() {
        try (Scanner reader = new Scanner(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            if (!reader.hasNextLine()) return;

            String jsonRequest = reader.nextLine();

            TypeToken<Request<Ticket>> typeToken = new TypeToken<Request<Ticket>>() {};
            Request<Ticket> request = new Gson().fromJson(jsonRequest, typeToken.getType());

            String action = request.getHeaders().get("action");

            Response<Ticket> response;

            if ("search".equals(action)) {

                TicketController controller = (TicketController) factory.getController("ticket");

                String movieToSearch = request.getBody().getEventName();
                System.out.println("Client is searching for: " + movieToSearch);

                Ticket result = controller.searchTicket(movieToSearch);

                if (result != null) {
                    System.out.println("Server found matching movie: " + result.getEventName());
                    response = new Response<>("success", result);
                } else {
                    System.out.println("No match found in database for: " + movieToSearch);
                    response = new Response<>("success", null);
                }


            } else if ("getTakenSeats".equals(action)) {

                String movie = request.getHeaders().get("movie");
                String date = request.getHeaders().get("date");
                String time = request.getHeaders().get("time");

                String key = movie + "|" + date + "|" + time;

                Set<String> taken = takenSeatsByShow.getOrDefault(key, new HashSet<>());
                String seatsCsv = String.join(",", taken);

                response = new Response<>("success", null);
                response.getHeaders().put("seats", seatsCsv);

            } else if ("bookSeats".equals(action)) {

                String movie = request.getHeaders().get("movie");
                String date = request.getHeaders().get("date");
                String time = request.getHeaders().get("time");
                String seatsCsv = request.getHeaders().get("seats");

                String key = movie + "|" + date + "|" + time;

                Set<String> taken = takenSeatsByShow.computeIfAbsent(key, k -> new HashSet<>());

                List<String> seatsToBook = Arrays.asList(seatsCsv.split(","));

                for (String seat : seatsToBook) {
                    if (taken.contains(seat)) {
                        response = new Response<>("error", null);
                        writer.println(new Gson().toJson(response));
                        writer.flush();
                        return;
                    }
                }

                taken.addAll(seatsToBook);
                response = new Response<>("success", null);

            }else if ("addMovie".equals(action)) {

                Ticket newMovie = request.getBody();
                if (newMovie == null || newMovie.getEventName() == null || newMovie.getEventName().isBlank()) {
                    response = new Response<>("error", null);
                } else {


                    TicketController controller = (TicketController) factory.getController("ticket");
                    controller.addTicket(newMovie);

                    response = new Response<>("success", null);
                }
            }

            else if ("getAllMovies".equals(action)) {
                TicketController controller = (TicketController) factory.getController("ticket");


                List<Ticket> allMovies = controller.getAllMovies();


                Response<List<Ticket>> listResponse = new Response<>("success", allMovies);


                writer.println(new Gson().toJson(listResponse));
                writer.flush();
                return;
            }



            else if ("deleteMovie".equals(action)) {
                Ticket movieToDelete = request.getBody();
                if (movieToDelete != null && movieToDelete.getEventName() != null) {
                    TicketController controller = (TicketController) factory.getController("ticket");

                    // שלב א': נמצא את הסרט המלא במאגר לפי השם
                    Ticket fullTicket = controller.getTicket(movieToDelete.getEventName());

                    if (fullTicket != null) {
                        // שלב ב': נמחק את האובייקט שנמצא
                        controller.deleteTicket(fullTicket);
                        response = new Response<>("success", null);
                    } else {
                        response = new Response<>("error", null); // הסרט לא נמצא
                    }
                } else {
                    response = new Response<>("error", null);
                }
            }

            else {
                response = new Response<>("error", null);
            }

            // שליחת תשובה ללקוח
            writer.println(new Gson().toJson(response));
            writer.flush();

        } catch (IOException e) {
            System.err.println("HandleRequest error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
