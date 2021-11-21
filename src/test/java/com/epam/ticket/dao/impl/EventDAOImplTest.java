package com.epam.ticket.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Event;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.epam.ticket.model.impl.EventImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

class EventDAOImplTest {

    public static final String STORAGE = "storage";

    @Test
    void testFindAll() {
        //GIVEN
        Storage storage = mock(Storage.class);
        EventDAOImpl eventDAOImpl = new EventDAOImpl();

        //WHEN
        when(storage.getEventMap()).thenReturn(Collections.singletonMap(1L, new EventImpl()));
        ReflectionTestUtils.setField(eventDAOImpl, STORAGE, storage);

        //THEN
        assertEquals(1L, eventDAOImpl.findAll().size());
        verify(storage).getEventMap();
    }

    @Test
    void testFindOne() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        EventDAOImpl eventDAOImpl = new EventDAOImpl();
        Event event = getEvent();

        //WHEN
        when(storage.getEventMap()).thenReturn(Collections.singletonMap(1L, event));
        ReflectionTestUtils.setField(eventDAOImpl, STORAGE, storage);

        //THEN
        assertEquals(eventDAOImpl.findOne(1L).getId(), event.getId());
        verify(storage).getEventMap();
    }

    @Test
    void testFindOneThrowsEntityNotFoundException() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        EventDAOImpl eventDAOImpl = new EventDAOImpl();

        //WHEN
        when(storage.getEventMap()).thenReturn(Collections.emptyMap());
        ReflectionTestUtils.setField(eventDAOImpl, STORAGE, storage);

        //THEN
        assertThrows(EntityNotFoundException.class, () -> eventDAOImpl.findOne(1L));
        verify(storage).getEventMap();
    }

    @Test
    void testUpdateThrowsEntityNotFoundException() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        EventDAOImpl eventDAOImpl = new EventDAOImpl();

        //WHEN
        when(storage.getEventMap()).thenReturn(new HashMap<>());
        ReflectionTestUtils.setField(eventDAOImpl, STORAGE, storage);
        Event event = mock(Event.class);
        when(event.getId()).thenReturn(1L);

        //THEN
        assertThrows(EntityNotFoundException.class, () -> eventDAOImpl.update(event));
        verify(storage).getEventMap();
        verify(event).getId();
    }

    @Test
    void testUpdate() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        EventDAOImpl eventDAOImpl = new EventDAOImpl();
        Event event = getEvent();
        Event eventToUpdate = getEvent();
        eventToUpdate.setTitle("test_title");
        Map<Long, Event> eventMap = new HashMap<>();
        eventMap.put(1L, event);

        //WHEN
        when(storage.getEventMap()).thenReturn(eventMap);
        ReflectionTestUtils.setField(eventDAOImpl, STORAGE, storage);
        Event updatedEvent = eventDAOImpl.update(eventToUpdate);

        //THEN
        assertNotEquals(updatedEvent.getTitle(), event.getTitle());
    }

    @Test
    void testCreate() {
        //GIVEN
        Storage storage = mock(Storage.class);
        EventDAOImpl eventDAOImpl = new EventDAOImpl();
        Event event = getEvent();
        Map<Long, Event> eventMap = new HashMap<>();
        eventMap.put(1L, event);

        //WHEN
        when(storage.getEventMap()).thenReturn(eventMap);
        ReflectionTestUtils.setField(eventDAOImpl, STORAGE, storage);

        //THEN
        assertEquals(eventDAOImpl.create(event), event);
        verify(storage).getEventMap();
    }

    @Test
    void testRemove() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        EventDAOImpl eventDAOImpl = new EventDAOImpl();

        //WHEN
        when(storage.getEventMap()).thenReturn(new HashMap<>());
        ReflectionTestUtils.setField(eventDAOImpl, STORAGE, storage);

        //THEN
        assertThrows(EntityNotFoundException.class, () -> eventDAOImpl.remove(1L));
        verify(storage).getEventMap();
    }

    private Event getEvent() {
        final Event event = new EventImpl();
        event.setId(1L);
        event.setTitle("title");
        event.setDate(new Date());
        return event;
    }
}

