package com.epam.ticket.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.model.impl.TicketImpl;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class TicketDAOImplTest {

    public static final String STORAGE = "storage";

    @Test
    void testFindAll() {
        //GIVEN
        Storage storage = mock(Storage.class);
        TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();
        Ticket ticket = getTicket();

        //WHEN
        when(storage.getTicketMap()).thenReturn(Collections.singletonMap(1L, ticket));
        ReflectionTestUtils.setField(ticketDAOImpl, STORAGE, storage);

        //THEN
        assertEquals(1L, ticketDAOImpl.findAll().size());
        verify(storage).getTicketMap();
    }

    @Test
    void testFindOneThrowsEntityNotFoundException() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();

        //WHEN
        when(storage.getTicketMap()).thenReturn(new HashMap<>());
        ReflectionTestUtils.setField(ticketDAOImpl, STORAGE, storage);

        //THEN
        assertThrows(EntityNotFoundException.class, () -> ticketDAOImpl.findOne(1L));
        verify(storage).getTicketMap();
    }

    @Test
    void testFindOne() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();
        Ticket ticket = getTicket();

        //WHEN
        when(storage.getTicketMap()).thenReturn(Collections.singletonMap(1L, ticket));
        ReflectionTestUtils.setField(ticketDAOImpl, STORAGE, storage);

        //THEN
        assertEquals(ticketDAOImpl.findOne(1L).getId(), ticket.getId());
        verify(storage).getTicketMap();
    }

    @Test
    void testUpdateThrowsEntityNotFoundException() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();

        //WHEN
        when(storage.getTicketMap()).thenReturn(new HashMap<>(1));
        ReflectionTestUtils.setField(ticketDAOImpl, STORAGE, storage);
        Ticket ticket = mock(Ticket.class);
        when(ticket.getId()).thenReturn(1L);

        //THEN
        assertThrows(EntityNotFoundException.class, () -> ticketDAOImpl.update(ticket));
        verify(storage).getTicketMap();
        verify(ticket).getId();
    }

    @Test
    void testUpdate() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();
        Ticket ticket = getTicket();
        Ticket ticketToUpdate = getTicket();
        ticketToUpdate.setPlace(11);
        Map<Long, Ticket> ticketMap = new HashMap<>();
        ticketMap.put(1L, ticket);

        //WHEN
        when(storage.getTicketMap()).thenReturn(ticketMap);
        ReflectionTestUtils.setField(ticketDAOImpl, STORAGE, storage);
        Ticket updatedTicket = ticketDAOImpl.update(ticketToUpdate);

        //THEN
        assertNotEquals(updatedTicket.getPlace(), ticket.getPlace());
    }

    @Test
    void testCreate() {
        //GIVEN
        Storage storage = mock(Storage.class);
        TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();
        Ticket ticket = getTicket();
        Map<Long, Ticket> ticketMap = new HashMap<>();
        ticketMap.put(1L, ticket);

        //WHEN
        when(storage.getTicketMap()).thenReturn(ticketMap);
        ReflectionTestUtils.setField(ticketDAOImpl, STORAGE, storage);

        //THEN
        assertEquals(ticketDAOImpl.create(ticket), ticket);
        verify(storage).getTicketMap();
    }

    @Test
    void testRemove() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();
        Ticket ticket = getTicket();
        Map<Long, Ticket> ticketMap = new HashMap<>();
        ticketMap.put(1L, ticket);

        //WHEN
        when(storage.getTicketMap()).thenReturn(ticketMap);
        ReflectionTestUtils.setField(ticketDAOImpl, STORAGE, storage);

        //THEN
        assertTrue(ticketDAOImpl.remove(1L));
    }

    @Test
    void testFindMaxId() {
        //GIVEN
        Storage storage = mock(Storage.class);
        when(storage.getTicketMap()).thenReturn(Collections.singletonMap(1L, new TicketImpl()));
        TicketDAOImpl ticketDAOImpl = new TicketDAOImpl();

        //WHEN
        ReflectionTestUtils.setField(ticketDAOImpl, STORAGE, storage);

        //THEN
        assertEquals(1L, ticketDAOImpl.findMaxId().longValue());
        verify(storage).getTicketMap();
    }

    private Ticket getTicket() {
        final Ticket ticket = new TicketImpl();
        ticket.setId(1L);
        ticket.setUserId(1L);
        ticket.setEventId(1L);
        ticket.setPlace(10);
        ticket.setCategory(String.valueOf(Ticket.Category.STANDARD));
        return ticket;
    }
}

