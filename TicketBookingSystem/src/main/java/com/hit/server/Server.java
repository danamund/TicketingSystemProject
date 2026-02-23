package com.hit.server;

import com.hit.algorithm.LcsDynamicAlgoImpl;
import com.hit.controller.ControllerFactory;
import com.hit.controller.TicketController;
import com.hit.service.TicketService;
import com.hit.dao.DaoFileImpl;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private int port;
    private ServerSocket server;
    private boolean isServerUp;
    private TicketController ticketController;


    public Server(int port) {
        this.port = port;
        this.isServerUp = true;
    }

    @Override
    public void run() {
        try {
            ControllerFactory controllerFactory = new ControllerFactory();

            server = new ServerSocket(port);
            System.out.println("Server is up and listening on port: " + port);

            while (isServerUp) {
                Socket clientSocket = server.accept();

                TicketController ticketController = (TicketController) controllerFactory.getController("ticket");

                HandleRequest handleRequest = new HandleRequest(clientSocket, ticketController);
                new Thread(handleRequest).start();
            }
        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        } finally {
            stopServer();
        }
    }

    public void stopServer() {
        isServerUp = false;
        try {
            if (server != null && !server.isClosed()) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}