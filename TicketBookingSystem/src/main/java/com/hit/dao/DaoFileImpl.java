package com.hit.dao;

import com.hit.dm.Ticket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class DaoFileImpl implements IDao{

    String filePath;

    public DaoFileImpl(String filePath)
    {
        this.filePath=filePath;
    }

    @Override
    public boolean save(Ticket t) {

        List<Ticket> allTickets = getTickets();

        allTickets.add(t);

        return writeListToFile(allTickets);
    }

    @Override
    public boolean delete(Ticket t) {

        List<Ticket> allTickets = getTickets();


        boolean removed = allTickets.remove(t);


        if (removed) {
            return writeListToFile(allTickets);
        }
        return false;
    }

    @Override
    public List<Ticket> getTickets() {
        List<Ticket> tickets = new ArrayList<>();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {

            tickets = (List<Ticket>) inputStream.readObject();
        } catch (FileNotFoundException e) {

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return tickets;
    }

    private boolean writeListToFile(List<Ticket> tickets) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(tickets);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
