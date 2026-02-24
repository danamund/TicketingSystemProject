package com.hit.server;

import com.hit.controller.ControllerFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {
    private int port;
    private ServerSocket server;
    private boolean isServerUp;

    public Server(int port) {
        this.port = port;
        this.isServerUp = true;
    }

    @Override
    public void run() {
        try {
            // יצירת ה-Factory פעם אחת בלבד עבור כל השרת
            ControllerFactory controllerFactory = new ControllerFactory();

            server = new ServerSocket(port);
            System.out.println("Server is up and listening on port: " + port);

            while (isServerUp) {
                Socket clientSocket = server.accept();
                System.out.println("New client connected!");

                // תיקון: שולחים את ה-Factory ל-HandleRequest
                HandleRequest handleRequest = new HandleRequest(clientSocket, controllerFactory);

                // הפעלת ה-Thread עבור הלקוח הספציפי
                new Thread(handleRequest).start();
            }
        } catch (IOException e) {
            if (isServerUp) {
                System.err.println("Server error: " + e.getMessage());
            }
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