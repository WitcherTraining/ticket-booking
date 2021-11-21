package com.epam.ticket.dao.impl;

import com.epam.ticket.dao.api.TicketDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.impl.TicketImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TicketDAOImpl implements TicketDAO {

    private static final Logger LOGGER = Logger.getLogger(TicketDAOImpl.class);

    @Autowired
    private Storage storage;

    @Override
    public List<Ticket> findAll() {
        return new ArrayList<>(this.storage.getTicketMap().values());
    }

    @Override
    public Ticket findOne(long id) throws EntityNotFoundException {
        Ticket ticket = this.storage.getTicketMap().get(id);
        if (Objects.nonNull(ticket)) {
            return ticket;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Ticket update(Ticket ticket) throws EntityNotFoundException {
        Ticket ticketToUpdate = this.storage.getTicketMap().get(ticket.getId());
        if (Objects.nonNull(ticketToUpdate)) {
            Ticket updatedTicket = this.mapTicket(ticket, ticketToUpdate);
            this.storage.getTicketMap().put(ticketToUpdate.getId(), updatedTicket);
            return updatedTicket;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Ticket create(Ticket entity) {
        this.storage.getTicketMap().put(entity.getId(), entity);
        return entity;
    }

    @Override
    public boolean remove(long id) throws EntityNotFoundException {
        Ticket ticket = this.storage.getTicketMap().get(id);
        if (Objects.nonNull(ticket)) {
            return this.storage.getTicketMap().remove(id, ticket);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Long findMaxId() {
        return this.storage.getTicketMap().keySet().stream().max(Long::compareTo).orElse(0L);
    }

    private Ticket mapTicket(Ticket ticket, Ticket ticketToUpdate) {
        final Ticket updatedTicket = new TicketImpl();
        updatedTicket.setCategory(Objects.nonNull(ticket.getCategory()) ? ticket.getCategory() : ticketToUpdate.getCategory());
        updatedTicket.setEventId(ticket.getEventId());
        updatedTicket.setUserId(ticket.getUserId());
        updatedTicket.setPlace(ticket.getPlace());
        return updatedTicket;
    }
}
