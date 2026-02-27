package com.hit.controller;

import com.hit.dm.Ticket;
import com.hit.service.TicketService;

import java.util.List;

/*
Submitted by:
Dana Mund, ID-319126074
Loren Kricheli ID-322632183

*/

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }


    public Ticket getTicket(String name) {
        return ticketService.getTicketByName(name);
    }

    public Ticket searchTicket(String query) {


        return ticketService.searchTicket(query);
    }
    public void addTicket(Ticket t) {
        ticketService.addTicket(t);
    }




    public List<Ticket> getAllMovies() {
        return ticketService.getAll();
    }
    public void saveTicket(Ticket ticket) {
        ticketService.addTicket(ticket);
    }

    public void deleteTicket(Ticket ticket) {
        ticketService.deleteTicket(ticket);
    }
}