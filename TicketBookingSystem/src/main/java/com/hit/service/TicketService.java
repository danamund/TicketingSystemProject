package com.hit.service;

import com.hit.algorithm.IAlgoStringMatching;
import com.hit.dm.Ticket;
import com.hit.dao.IDao;
import java.util.List;

public class TicketService {
    private IDao<Ticket> dao;
    private IAlgoStringMatching algo;


    public TicketService(IDao<Ticket> dao, IAlgoStringMatching algo) {
        this.dao = dao;
        this.algo = algo;
    }
    public Ticket getTicketByName(String name) {
        List<Ticket> allTickets = dao.getAll();

        // ניקוי הקלט: הופכים לאותיות קטנות ומסירים רווחים
        String searchName = name.toLowerCase().replaceAll("\\s+", "");

        for (Ticket t : allTickets) {
            // ניקוי שם הסרט מהקובץ באותה צורה
            String ticketName = t.getEventName().toLowerCase().replaceAll("\\s+", "");

            // עכשיו האלגוריתם ימצא התאמה מושלמת!
            int lcsLength = algo.getCommonLength(ticketName, searchName);
            double similarity = (double) lcsLength / Math.max(ticketName.length(), searchName.length());

            if (similarity > 0.5) {
                return t; // מחזיר את האובייקט המקורי עם השם הנכון לתמונה (למשל IronMan)
            }
        }
        return null;
    }
    public void addTicket(Ticket ticket) {
        dao.save(ticket);
    }

    public List<Ticket> getAll() {
        return dao.getAll();
    }

    public void deleteTicket(Ticket t) {
        dao.delete(t);
    }

    public Ticket searchTicket(String query) {
        List<Ticket> allTickets = dao.getAll();
        Ticket bestMatch = null;
        int maxScore = 0;


        for (Ticket t : allTickets) {
            int score = algo.getCommonLength(query, t.getEventName());

            if (score > maxScore) {
                maxScore = score;
                bestMatch = t;
            }
        }
        return bestMatch;
    }
}