package com.hit.dm;

import java.util.Objects;

public class Ticket implements java.io.Serializable{
    private long id;
    private String eventName;
    private String customerName;
    private double price;


    public Ticket() {
    }

    public Ticket(long id,String eventName,String customerName,double price)
    {
        this.id=id;
        this.eventName=eventName;
        this.customerName=customerName;
        this.price=price;
    }

    public long getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getPrice() {
        return price;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return id == ticket.id && Double.compare(price, ticket.price) == 0 && Objects.equals(eventName, ticket.eventName) && Objects.equals(customerName, ticket.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventName, customerName, price);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", Event Name='" + eventName + '\'' +
                ", Customer Name='" + customerName + '\'' +
                ", Price=" + price +
                '}';
    }
}

