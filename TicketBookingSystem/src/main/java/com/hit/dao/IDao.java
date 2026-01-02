package com.hit.dao;

import com.hit.dm.Ticket;
import java.util.List;

public interface IDao<T> {

    boolean save(Ticket t);

    boolean delete(Ticket t);

    List<Ticket> getTickets();
}

