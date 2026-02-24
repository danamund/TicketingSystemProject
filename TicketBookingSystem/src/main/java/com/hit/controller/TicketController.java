package com.hit.controller;

import com.hit.dm.Ticket;
import com.hit.service.TicketService;

public class TicketController {
    private TicketService ticketService;


    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public Ticket getTicket(String name) {
        // קורא ל-Service שמשתמש באלגוריתם ה-LCS שמימשת
        return ticketService.getTicketByName(name);
    }

    public Ticket searchTicket(String query) {
        return ticketService.searchTicket(query);
    }


    public void saveTicket(Ticket ticket) {
        ticketService.addTicket(ticket);
    }


    public void deleteTicket(Ticket ticket) {
        ticketService.deleteTicket(ticket);
    }
}