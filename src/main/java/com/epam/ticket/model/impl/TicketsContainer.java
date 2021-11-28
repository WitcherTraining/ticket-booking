package com.epam.ticket.model.impl;

import java.util.ArrayList;
import java.util.List;

public class TicketsContainer {
    private List<TicketImpl> tickets = new ArrayList<>();

    public List<TicketImpl> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketImpl> tickets) {
        this.tickets = tickets;
    }
}
