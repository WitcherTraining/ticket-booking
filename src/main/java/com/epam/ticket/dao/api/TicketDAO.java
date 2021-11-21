package com.epam.ticket.dao.api;

import com.epam.ticket.model.api.Ticket;

public interface TicketDAO extends BaseDAO<Ticket> {

    Long findMaxId();
}
