package com.hit.service;

import com.hit.algorithm.IAlgoStringMatching;
import com.hit.algorithm.LcsDynamicAlgoImpl;
import com.hit.dm.Ticket;
import com.hit.dao.IDao;
import java.util.List;

public class TicketService {
    private IDao<Ticket> dao;

    public TicketService(IDao<Ticket> dao) {
        this.dao = dao;
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

        IAlgoStringMatching algo = new LcsDynamicAlgoImpl();

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