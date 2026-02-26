package com.hit.controller;

import com.hit.algorithm.LcsDynamicAlgoImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.Ticket; // ודאי שייבאת את ה-DM
import com.hit.service.TicketService;
import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {
    private Map<String, Object> controllers = new HashMap<>();

    public ControllerFactory() {

        String path = "src/main/resources/datasource.txt";
        DaoFileImpl<Ticket> dao = new DaoFileImpl<>(path, Ticket.class);
        TicketService ticketService = new TicketService(dao, new LcsDynamicAlgoImpl());
        controllers.put("ticket", new TicketController(ticketService));

    }

    public Object getController(String type) {
        return controllers.get(type.toLowerCase());
    }
}