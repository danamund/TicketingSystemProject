package com.hit.service;

import com.hit.algorithm.IAlgoStringMatching;
import com.hit.dm.Ticket;
import com.hit.dao.IDao;
import java.util.List;

public class TicketService {
    private IDao<Ticket> dao;
    private IAlgoStringMatching algo; // 1. הוספת משתנה לאלגוריתם (Strategy)

    // 2. עדכון הקונסטרקטור שיקבל גם את האלגוריתם מבחוץ
    public TicketService(IDao<Ticket> dao, IAlgoStringMatching algo) {
        this.dao = dao;
        this.algo = algo;
    }

    public void addTicket(Ticket ticket) {
        dao.save(ticket);
    }

    public List<Ticket> getAll() {
        return dao.getTickets();
    }

    public void deleteTicket(Ticket t) {
        dao.delete(t);
    }

    public Ticket searchTicket(String query) {
        List<Ticket> allTickets = dao.getTickets();
        Ticket bestMatch = null;
        int maxScore = 0;

        // 3. שימוש באלגוריתם שהתקבל מבחוץ (בלי new בתוך הפונקציה!)
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