package com.epam.ticket.service.impl;

import com.epam.ticket.dao.api.EventDAO;
import com.epam.ticket.dao.api.TicketDAO;
import com.epam.ticket.dao.api.UserDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.impl.TicketImpl;
import com.epam.ticket.service.api.TicketService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    private static final Logger LOGGER = Logger.getLogger(TicketServiceImpl.class);

    @Autowired
    private TicketDAO ticketDao;
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private EventDAO eventDAO;

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        User user;
        Event event;

        try {
            user = this.userDAO.findOne(userId);
            event = this.eventDAO.findOne(eventId);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Entity not found", e);
            return new TicketImpl();
        }

        final long generatedId = ticketDao.findMaxId() + 1L;

        Ticket ticket = new TicketImpl();
        ticket.setId(generatedId);
        ticket.setEventId(event.getId());
        ticket.setUserId(user.getId());
        ticket.setPlace(place);
        ticket.setCategory(category);

        return this.ticketDao.create(ticket);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return this.ticketDao.findAll()
                .stream()
                .filter(ticket -> Objects.equals(ticket.getUserId(), user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return this.ticketDao.findAll()
                .stream()
                .filter(ticket -> Objects.equals(ticket.getUserId(), event.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        try {
            return this.ticketDao.remove(ticketId);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot cancel ticket with ID [%s]", ticketId), e);
        }
        return Boolean.FALSE;
    }
}
