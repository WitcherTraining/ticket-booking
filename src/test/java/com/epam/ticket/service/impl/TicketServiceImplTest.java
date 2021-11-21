package com.epam.ticket.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.dao.api.EventDAO;
import com.epam.ticket.dao.api.TicketDAO;
import com.epam.ticket.dao.api.UserDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class TicketServiceImplTest {

    public static final String USER_DAO = "userDAO";
    public static final String TICKET_DAO = "ticketDao";
    public static final String EVENT_DAO = "eventDAO";

    @Test
    void testBookTicket() throws EntityNotFoundException {
        //GIVEN
        User user = mock(User.class);
        when(user.getId()).thenReturn(1L);
        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.findOne(anyLong())).thenReturn(user);
        EventDAO eventDAO = mock(EventDAO.class);
        TicketDAO ticketDAO = mock(TicketDAO.class);
        when(ticketDAO.create(any())).thenReturn(mock(Ticket.class));
        Event event = mock(Event.class);
        when(ticketDAO.findMaxId()).thenReturn(1L);
        TicketServiceImpl ticketServiceImpl = new TicketServiceImpl();

        //WHEN
        ReflectionTestUtils.setField(ticketServiceImpl, EVENT_DAO, eventDAO);
        ReflectionTestUtils.setField(ticketServiceImpl, USER_DAO, userDAO);
        ReflectionTestUtils.setField(ticketServiceImpl, TICKET_DAO, ticketDAO);
        when(event.getId()).thenReturn(1L);
        when(eventDAO.findOne(anyLong())).thenReturn(event);
        ticketServiceImpl.bookTicket(1L, 1L, 1, Ticket.Category.STANDARD);

        //THEN
        verify(userDAO).findOne(anyLong());
        verify(user).getId();
        verify(ticketDAO).create(any());
        verify(ticketDAO).findMaxId();
        verify(eventDAO).findOne(anyLong());
        verify(event).getId();
    }

    @Test
    void testGetBookedTickets() {
        //GIVEN
        TicketServiceImpl ticketServiceImpl = new TicketServiceImpl();
        Ticket ticket = mock(Ticket.class);
        when(ticket.getUserId()).thenReturn(1L);
        ArrayList<Ticket> ticketList = new ArrayList<>();
        ticketList.add(ticket);
        TicketDAO ticketDAO = mock(TicketDAO.class);

        //WHEN
        when(ticketDAO.findAll()).thenReturn(ticketList);
        ReflectionTestUtils.setField(ticketServiceImpl, USER_DAO, mock(UserDAO.class));
        ReflectionTestUtils.setField(ticketServiceImpl, TICKET_DAO, ticketDAO);
        ReflectionTestUtils.setField(ticketServiceImpl, EVENT_DAO, mock(EventDAO.class));
        Event event = mock(Event.class);
        when(event.getId()).thenReturn(1L);

        //THEN
        assertEquals(1, ticketServiceImpl.getBookedTickets(event, 1, 1).size());
        verify(ticketDAO).findAll();
        verify(ticket).getUserId();
        verify(event).getId();
    }

    @Test
    void testCancelTicket() throws EntityNotFoundException {
        //GIVEN
        TicketServiceImpl ticketServiceImpl = new TicketServiceImpl();
        TicketDAO ticketDAO = mock(TicketDAO.class);

        //WHEN
        when(ticketDAO.remove(anyLong())).thenReturn(true);
        ReflectionTestUtils.setField(ticketServiceImpl, USER_DAO, mock(UserDAO.class));
        ReflectionTestUtils.setField(ticketServiceImpl, TICKET_DAO, ticketDAO);
        ReflectionTestUtils.setField(ticketServiceImpl, EVENT_DAO, mock(EventDAO.class));

        //THEN
        assertTrue(ticketServiceImpl.cancelTicket(1L));
        verify(ticketDAO).remove(anyLong());
    }
}

