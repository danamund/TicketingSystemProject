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
        // תיקון 1: הוספת Ticket.class והגדרת סוג גנרי <Ticket>
        DaoFileImpl<Ticket> dao = new DaoFileImpl<>("src/main/resources/datasource.txt", Ticket.class);

        // יצירת ה-Service עם ה-DAO והאלגוריתם
        TicketService ticketService = new TicketService(dao, new LcsDynamicAlgoImpl());

        // תיקון 2: ודאי שיש לך TicketController בשרת שמקבל Service
        controllers.put("ticket", new TicketController(ticketService));
    }

    public Object getController(String type) {
        return controllers.get(type.toLowerCase());
    }
}