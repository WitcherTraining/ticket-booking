package com.epam.ticket.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.dao.EventDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.impl.TicketImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.*;

import com.epam.ticket.model.impl.EventImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class EventServiceImplTest {

    public static final String EVENT_DAO = "eventDAO";

    @Test
    void testGetEventById() throws EntityNotFoundException {
        //GIVEN
        EventImpl eventImpl = getEvent();
        EventDAO eventDAO = mock(EventDAO.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl();

        //WHEN
        ReflectionTestUtils.setField(eventServiceImpl, EVENT_DAO, eventDAO);
        when(eventDAO.findById(anyLong())).thenReturn(Optional.of(eventImpl));

        //THEN
        assertNotNull(eventServiceImpl.getEventById(1L));
        verify(eventDAO).findById(anyLong());
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
        EventImpl event = mock(EventImpl.class);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        ArrayList<EventImpl> eventList = new ArrayList<>();
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
        EventImpl event = getEvent();

        //WHEN
        when(eventDAO.save(event)).thenReturn(event);
        ReflectionTestUtils.setField(eventServiceImpl, EVENT_DAO, eventDAO);

        //THEN
        assertEquals(eventServiceImpl.createEvent(event), event);
        verify(eventDAO).save(any());
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
        verify(eventDAO).save(any());
    }

    @Test
    void testDeleteEvent() throws EntityNotFoundException {
        //GIVEN
        EventDAO eventDAO = mock(EventDAO.class);
        EventServiceImpl eventServiceImpl = new EventServiceImpl();

        //WHEN
        ReflectionTestUtils.setField(eventServiceImpl, EVENT_DAO, eventDAO);

        //THEN
        assertTrue(eventServiceImpl.deleteEvent(1L));
        verify(eventDAO).deleteById(anyLong());
    }

    private EventImpl getEvent() {
        final EventImpl event = new EventImpl();
        event.setId(1L);
        event.setTitle("title");
        event.setDate(new Date());
        return event;
    }
}

