package com.hit.service;

import com.hit.algorithm.IAlgoStringMatching; // הוספנו
import com.hit.algorithm.LcsDynamicAlgoImpl; // הוספנו
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.Ticket;
import org.junit.Assert;
import org.junit.Test;

public class TicketServiceTest {

    @Test
    public void testSearchTicketSuccess() {
        IDao dao = new DaoFileImpl("TicketBookingSystem/src/main/resources/datasource.txt");


        IAlgoStringMatching algo = new LcsDynamicAlgoImpl();
        TicketService service = new TicketService(dao, algo);

        Ticket t1 = new Ticket(1, "The Lion King", "Dana", 150.0);
        service.addTicket(t1);

        Ticket result = service.searchTicket("Lion");

        Assert.assertNotNull("Should find a ticket", result);
        Assert.assertEquals("The Lion King", result.getEventName());
    }

    @Test
    public void testSearchTicketNoMatch() {
        IDao dao = new DaoFileImpl("test_tickets.dat");


        IAlgoStringMatching algo = new LcsDynamicAlgoImpl();
        TicketService service = new TicketService(dao, algo);

        Ticket result = service.searchTicket("wwww");

        Assert.assertNull("Should not find a match for 'wwww'", result);
    }
}