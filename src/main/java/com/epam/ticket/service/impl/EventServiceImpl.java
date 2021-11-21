package com.epam.ticket.service.impl;

import com.epam.ticket.dao.api.EventDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.service.api.EventService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private static final Logger LOGGER = Logger.getLogger(EventServiceImpl.class);

    @Autowired
    private EventDAO eventDAO;

    @Override
    public Event getEventById(long eventId) {
        try {
            return this.eventDAO.findOne(eventId);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot find event with ID [%s]", eventId), e);
        }

        return new EventImpl();
    }

    @Override
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        return this.eventDAO.findAll()
                .stream()
                .filter(event -> Objects.equals(event.getTitle(), title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        return this.eventDAO.findAll()
                .stream()
                .filter(event -> Objects.equals(event.getDate(), day))
                .collect(Collectors.toList());
    }

    @Override
    public Event createEvent(Event event) {
        return this.eventDAO.create(event);
    }

    @Override
    public Event updateEvent(Event event) {
        try {
            return this.eventDAO.update(event);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot find event [%s]", event), e);
        }

        return new EventImpl();
    }

    @Override
    public boolean deleteEvent(long eventId) {
        try {
            return this.eventDAO.remove(eventId);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot remove event with ID [%s]", eventId), e);
        }
        return Boolean.FALSE;
    }
}
