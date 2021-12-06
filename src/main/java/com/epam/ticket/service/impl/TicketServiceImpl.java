package com.epam.ticket.service.impl;

import com.epam.ticket.dao.EventDAO;
import com.epam.ticket.dao.TicketDAO;
import com.epam.ticket.dao.UserAccountDAO;
import com.epam.ticket.dao.UserDAO;
import com.epam.ticket.exception.BalanceException;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.api.UserAccount;
import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.model.impl.TicketImpl;
import com.epam.ticket.model.impl.UserAccountImpl;
import com.epam.ticket.model.impl.UserImpl;
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
    private UserAccountDAO userAccountDAO;
    @Autowired
    private EventDAO eventDAO;

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        User user;
        UserAccount userAccount;
        Event event;

        try {
            user = this.userDAO.findById(userId).orElseThrow(EntityNotFoundException::new);
            event = this.eventDAO.findById(eventId).orElseThrow(EntityNotFoundException::new);
            userAccount = this.userAccountDAO.findById(userId).orElseThrow(EntityNotFoundException::new);
            int updatedBalance = checkBalanceAndPay(userAccount.getPrepaidMoney(), event.getTicketPrice());
            userAccount.setPrepaidMoney(updatedBalance);
        } catch (EntityNotFoundException e) {
            LOGGER.error("Entity not found", e);
            return new TicketImpl();
        }

        this.userAccountDAO.save((UserAccountImpl) userAccount);

        TicketImpl ticket = mapTicket(place, category, user, event);

        return this.ticketDao.save(ticket);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        return this.ticketDao.findAll()
                .stream()
                .filter(ticket -> Objects.equals(ticket.getUser().getId(), user.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        return this.ticketDao.findAll()
                .stream()
                .filter(ticket -> Objects.equals(ticket.getEvent().getId(), event.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        try {
            this.ticketDao.deleteById(ticketId);
            return !this.ticketDao.existsById(ticketId);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot cancel ticket with ID [%s]", ticketId), e);
        }
        return Boolean.FALSE;
    }

    private TicketImpl mapTicket(int place, Ticket.Category category, User user, Event event) {
        TicketImpl ticket = new TicketImpl();
        ticket.setEvent((EventImpl) event);
        ticket.setUser((UserImpl) user);
        ticket.setPlace(place);
        ticket.setCategory(String.valueOf(category));
        return ticket;
    }

    private int checkBalanceAndPay(int prepaidMoney, int ticketPrice) {
        if (prepaidMoney <= 0 || ticketPrice >= prepaidMoney) {
            LOGGER.warn(String.format("Not enough money, account's balance: [%d]", prepaidMoney));
            throw new BalanceException("Sorry, but you haven't enough money on account");
        } else {
            return prepaidMoney - ticketPrice;
        }
    }

}
