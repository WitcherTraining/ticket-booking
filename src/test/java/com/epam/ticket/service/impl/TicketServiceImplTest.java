package com.epam.ticket.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.dao.EventDAO;
import com.epam.ticket.dao.TicketDAO;
import com.epam.ticket.dao.UserAccountDAO;
import com.epam.ticket.dao.UserDAO;
import com.epam.ticket.exception.BalanceException;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.model.impl.TicketImpl;
import com.epam.ticket.model.impl.UserAccountImpl;
import com.epam.ticket.model.impl.UserImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class TicketServiceImplTest {

    public static final String USER_DAO = "userDAO";
    public static final String TICKET_DAO = "ticketDao";
    public static final String EVENT_DAO = "eventDAO";
    public static final String USER_ACCOUNT_DAO = "userAccountDAO";

    @Test
    void testBookTicket() {
        //GIVEN
        UserImpl userImpl = getUser();
        UserDAO userDAO = mock(UserDAO.class);
        when(userDAO.findById(any())).thenReturn(Optional.of(userImpl));
        TicketServiceImpl ticketServiceImpl = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketServiceImpl, USER_DAO, userDAO);
        UserImpl userImpl1 = getUser();
        UserAccountImpl userAccountImpl = getUserAccount(userImpl1);
        UserAccountDAO userAccountDAO = mock(UserAccountDAO.class);
        when(userAccountDAO.findById(any())).thenReturn(Optional.of(userAccountImpl));
        ReflectionTestUtils.setField(ticketServiceImpl, USER_ACCOUNT_DAO, userAccountDAO);
        ReflectionTestUtils.setField(ticketServiceImpl, TICKET_DAO, mock(TicketDAO.class));
        EventImpl eventImpl = getEvent();
        EventDAO eventDAO = mock(EventDAO.class);

        //WHEN
        when(eventDAO.findById(any())).thenReturn(Optional.of(eventImpl));
        ReflectionTestUtils.setField(ticketServiceImpl, EVENT_DAO, eventDAO);

        //THEN
        assertThrows(BalanceException.class, () -> ticketServiceImpl.bookTicket(1L, 1L, 1, Ticket.Category.STANDARD));
        verify(userDAO).findById(any());
        verify(userAccountDAO).findById(any());
        verify(eventDAO).findById(any());
    }

    private EventImpl getEvent() {
        EventImpl eventImpl = new EventImpl();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        eventImpl.setDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        eventImpl.setTicketPrice(1);
        eventImpl.setId(1L);
        eventImpl.setTickets(new ArrayList<>());
        eventImpl.setTitle("title");
        return eventImpl;
    }

    private UserAccountImpl getUserAccount(UserImpl userImpl1) {
        UserAccountImpl userAccountImpl = new UserAccountImpl();
        userAccountImpl.setId(1L);
        userAccountImpl.setPrepaidMoney(1);
        userAccountImpl.setUser(userImpl1);
        return userAccountImpl;
    }

    private UserImpl getUser() {
        UserImpl userImpl = new UserImpl();
        userImpl.setEmail("example@example.org");
        userImpl.setId(1L);
        userImpl.setName("Name");
        userImpl.setTickets(new ArrayList<>());
        userImpl.setUserAccounts(new ArrayList<>());
        return userImpl;
    }

    @Test
    void testGetBookedTickets() {
        //GIVEN
        TicketServiceImpl ticketServiceImpl = new TicketServiceImpl();
        ReflectionTestUtils.setField(ticketServiceImpl, USER_DAO, mock(UserDAO.class));
        ReflectionTestUtils.setField(ticketServiceImpl, USER_ACCOUNT_DAO, mock(UserAccountDAO.class));
        TicketDAO ticketDAO = mock(TicketDAO.class);

        //WHEN
        when(ticketDAO.findAll()).thenReturn(new ArrayList<>());
        ReflectionTestUtils.setField(ticketServiceImpl, TICKET_DAO, ticketDAO);
        ReflectionTestUtils.setField(ticketServiceImpl, EVENT_DAO, mock(EventDAO.class));

        //THEN
        assertTrue(ticketServiceImpl.getBookedTickets(mock(Event.class), 1, 1).isEmpty());
        verify(ticketDAO).findAll();
    }

    @Test
    void testCancelTicket() throws EntityNotFoundException {
        //GIVEN
        TicketServiceImpl ticketServiceImpl = new TicketServiceImpl();
        TicketDAO ticketDAO = mock(TicketDAO.class);

        //WHEN
        when(ticketDAO.existsById(any())).thenReturn(false);
        doNothing().when(ticketDAO).deleteById(any());
        ReflectionTestUtils.setField(ticketServiceImpl, USER_DAO, mock(UserDAO.class));
        ReflectionTestUtils.setField(ticketServiceImpl, TICKET_DAO, ticketDAO);
        ReflectionTestUtils.setField(ticketServiceImpl, EVENT_DAO, mock(EventDAO.class));

        //THEN
        assertTrue(ticketServiceImpl.cancelTicket(1L));
        verify(ticketDAO).deleteById(anyLong());
    }

}

