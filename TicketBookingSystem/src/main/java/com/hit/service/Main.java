package com.hit.service;

import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.Ticket;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Starting Ticket System Smoke Test ---");

        IDao dao = new DaoFileImpl("datasource.txt");

        TicketService service = new TicketService(dao);

        Ticket t1 = new Ticket(1, "The Lion King", "Dana",150.0);
        Ticket t2 = new Ticket(2, "Frozen Musical", "Loren",200.0);

        service.addTicket(t1);
        service.addTicket(t2);
        System.out.println("Successfully added 2 tickets to the file.");

        List<Ticket> allTickets = service.getAll();
        System.out.println("Total tickets found in file: " + allTickets.size());

        System.out.println("Testing smart search for query: 'wwww'...");
        Ticket found = service.searchTicket("wwww");

        if (found != null) {
            System.out.println("Result found! Event Name: " + found.getEventName());
        } else {
            System.out.println("Search failed to find a match.");
        }

        System.out.println("--- Smoke Test Completed ---");
    }
}