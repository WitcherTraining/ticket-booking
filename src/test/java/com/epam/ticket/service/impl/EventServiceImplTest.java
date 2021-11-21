package com.epam.ticket.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.dao.api.EventDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Event;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.*;

import com.epam.ticket.model.impl.EventImpl;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class EventServiceImplTest {

    public static final String EVENT_DAO = "eventDAO";

    @Test
    void testGetEventById() throws EntityNotFoundException {
        //GIVEN
        EventDAO eventDAO = mock(EventDAO.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl();

        //WHEN
        ReflectionTestUtils.setField(eventServiceImpl, EVENT_DAO, eventDAO);
        when(eventDAO.findOne(anyLong())).thenReturn(mock(Event.class));

        //THEN
        assertNotNull(eventServiceImpl.getEventById(1L));
        verify(eventDAO).findOne(anyLong());
    }

    @Test
    void testGetEventsByTitle() {
        //GIVEN
        EventDAO eventDAO = mock(EventDAO.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl();

        //WHEN
        ReflectionTestUtils.setField(eventServiceImpl, EVENT_DAO, eventDAO);
        when(eventDAO.findAll()).thenReturn(Collections.singletonList(getEvent()));

        //THEN
        assertEquals(1, eventServiceImpl.getEventsByTitle("title", 1, 1).size());
        verify(eventDAO).findAll();
    }

    @Test
    void testGetEventsForDay() {
        //GIVEN
        Event event = mock(Event.class);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        ArrayList<Event> eventList = new ArrayList<>();
        eventList.add(event);
        EventDAO eventDAO = mock(EventDAO.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl();

        //WHEN
        when(event.getDate()).thenReturn(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        when(eventDAO.findAll()).thenReturn(eventList);
        ReflectionTestUtils.setField(eventServiceImpl, EVENT_DAO, eventDAO);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();

        //THEN
        assertEquals(1,
                eventServiceImpl.getEventsForDay(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC"))
                                .toInstant()), 1, 1).size());
        verify(eventDAO).findAll();
        verify(event).getDate();
    }

    @Test
    void testCreateEvent() {
        //GIVEN
        EventDAO eventDAO = mock(EventDAO.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl();
        Event event = getEvent();

        //WHEN
        when(eventDAO.create(event)).thenReturn(event);
        ReflectionTestUtils.setField(eventServiceImpl, EVENT_DAO, eventDAO);

        //THEN
        assertEquals(eventServiceImpl.createEvent(event), event);
        verify(eventDAO).create(any());
    }

    @Test
    void testUpdateEvent() throws EntityNotFoundException {
        //GIVEN
        EventDAO eventDAO = mock(EventDAO.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl();
        Event event = getEvent();
        Event updatedEvent = getEvent();
        updatedEvent.setTitle("test_title");

        //WHEN
        ReflectionTestUtils.setField(eventServiceImpl, EVENT_DAO, eventDAO);
        final Event actualUpdated = eventServiceImpl.updateEvent(updatedEvent);

        //THEN
        assertNotEquals(actualUpdated, event);
        verify(eventDAO).update(any());
    }

    @Test
    void testDeleteEvent() throws EntityNotFoundException {
        //GIVEN
        EventDAO eventDAO = mock(EventDAO.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl();

        //WHEN
        ReflectionTestUtils.setField(eventServiceImpl, EVENT_DAO, eventDAO);

        //THEN
        assertFalse(eventServiceImpl.deleteEvent(1L));
        verify(eventDAO).remove(anyLong());
    }

    private Event getEvent() {
        final Event event = new EventImpl();
        event.setId(1L);
        event.setTitle("title");
        event.setDate(new Date());
        return event;
    }
}

