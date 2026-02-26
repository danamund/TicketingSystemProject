package com.hit.controller;

import com.hit.dm.Ticket;
import com.hit.service.TicketService;

import java.util.List;

public class TicketController {
    private TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    /**
     * מחפש כרטיס לפי שם מדויק.
     * פחות מומלץ לשימוש בחיפוש חופשי מה-UI.
     */
    public Ticket getTicket(String name) {
        return ticketService.getTicketByName(name);
    }

    public Ticket searchTicket(String query) {
        // קורא ללוגיקה ב-Service שמפעילה את ה-IAlgoStringMatching
        return ticketService.searchTicket(query);
    }
    public void addTicket(Ticket t) {
        // אם יש לך List/Ticket[] במאגר – להוסיף לשם.
        // לדוגמה:
        ticketService.addTicket(t);
    }

;

    // בתוך TicketController.java שבחבילת com.hit.controller (בשרת)
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