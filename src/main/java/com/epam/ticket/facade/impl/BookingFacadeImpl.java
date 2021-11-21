package com.epam.ticket.facade.impl;

import com.epam.ticket.facade.api.BookingFacade;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;
import com.epam.ticket.service.api.EventService;
import com.epam.ticket.service.api.TicketService;
import com.epam.ticket.service.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BookingFacadeImpl implements BookingFacade {
    private static final Logger LOGGER = Logger.getLogger(BookingFacadeImpl.class);

    private UserService userService;
    private TicketService ticketService;
    private final EventService eventService;

    public BookingFacadeImpl(UserService userService, TicketService ticketService, EventService eventService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.eventService = eventService;
    }

    @Override
    public Event getEventById(long eventId) {
        LOGGER.info(String.format("Trying to get event by ID: [%s]", eventId));
        return this.eventService.getEventById(eventId);
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        LOGGER.info(String.format("Trying to get events by title: [%s]", title));
        return this.eventService.getEventsByTitle(title, pageSize, pageNum);
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        LOGGER.info(String.format("Trying to get events by day: [%s]", day));
        return this.eventService.getEventsForDay(day, pageSize, pageNum);
    }

    @Override
    public Event createEvent(Event event) {
        LOGGER.info("Trying to create event...");
        return this.eventService.createEvent(event);
    }

    @Override
    public Event updateEvent(Event event) {
        LOGGER.info(String.format("Trying to update event: [%s]", event));
        return this.eventService.updateEvent(event);
    }

    @Override
    public boolean deleteEvent(long eventId) {
        LOGGER.info(String.format("Trying to delete event by ID: [%s]", eventId));
        return this.eventService.deleteEvent(eventId);
    }

    @Override
    public User getUserById(long userId) {
        LOGGER.info(String.format("Trying to get user by ID: [%s]", userId));
        return this.userService.getUserById(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        LOGGER.info(String.format("Trying to get user by email: [%s]", email));
        return this.userService.getUserByEmail(email);
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        LOGGER.info(String.format("Trying to get user by name: [%s]", name));
        return this.userService.getUsersByName(name, pageSize, pageNum);
    }

    @Override
    public User createUser(User user) {
        LOGGER.info("Trying to create user...");
        return this.userService.createUser(user);
    }

    @Override
    public User updateUser(User user) {
        LOGGER.info(String.format("Trying to update user: [%s]", user));
        return this.userService.updateUser(user);
    }

    @Override
    public boolean deleteUser(long userId) {
        LOGGER.info(String.format("Trying to delete user by ID: [%s]", userId));
        return this.userService.deleteUser(userId);
    }

    @Override
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        LOGGER.info("Trying to book ticket...");
        return this.ticketService.bookTicket(userId, eventId, place, category);
    }

    @Override
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        LOGGER.info(String.format("Trying to get tickets by user: [%s]", user));
        return this.ticketService.getBookedTickets(user, pageSize, pageNum);
    }

    @Override
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        LOGGER.info(String.format("Trying to get tickets by event: [%s]", event));
        return this.ticketService.getBookedTickets(event, pageSize, pageNum);
    }

    @Override
    public boolean cancelTicket(long ticketId) {
        LOGGER.info(String.format("Trying to cancel ticket by ID: [%s]", ticketId));
        return this.ticketService.cancelTicket(ticketId);
    }
}
