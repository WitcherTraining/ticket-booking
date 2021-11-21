package com.epam.ticket.dao.impl;

import com.epam.ticket.dao.api.EventDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.impl.EventImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class EventDAOImpl implements EventDAO {

    @Autowired
    private Storage storage;

    @Override
    public List<Event> findAll() {
        return new ArrayList<>(this.storage.getEventMap().values());
    }

    @Override
    public Event findOne(long id) throws EntityNotFoundException {
        Event event = this.storage.getEventMap().get(id);
        if (Objects.nonNull(event)) {
            return event;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Event update(Event event) throws EntityNotFoundException {
        Event eventToUpdate = this.storage.getEventMap().get(event.getId());
        if (Objects.nonNull(eventToUpdate)) {
            Event updatedEvent = this.mapEvent(event, eventToUpdate);
            storage.getEventMap().put(eventToUpdate.getId(), updatedEvent);
            return updatedEvent;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public Event create(Event event) {
        storage.getEventMap().put(event.getId(), event);
        return event;
    }

    @Override
    public boolean remove(long id) throws EntityNotFoundException {
        Event event = this.storage.getEventMap().get(id);
        if (Objects.nonNull(event)) {
            return storage.getEventMap().remove(id, event);
        } else {
            throw new EntityNotFoundException();
        }
    }

    private Event mapEvent(Event event, Event eventToUpdate) {
        final Event updatedEvent = new EventImpl();
        updatedEvent.setDate(Objects.nonNull(event.getDate()) ? event.getDate() : eventToUpdate.getDate());
        updatedEvent.setTitle(Objects.nonNull(event.getTitle()) ? event.getTitle() : eventToUpdate.getTitle());
        return updatedEvent;
    }
}
