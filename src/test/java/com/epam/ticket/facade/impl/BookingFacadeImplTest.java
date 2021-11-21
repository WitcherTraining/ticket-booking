package com.epam.ticket.facade.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.service.api.EventService;
import com.epam.ticket.service.api.TicketService;
import com.epam.ticket.service.api.UserService;
import com.epam.ticket.service.impl.EventServiceImpl;
import com.epam.ticket.service.impl.TicketServiceImpl;
import com.epam.ticket.service.impl.UserServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

class BookingFacadeImplTest {
    @Test
    void testGetEventById() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        UserServiceImpl userService = new UserServiceImpl();

        //WHEN
        (new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService)).getEventById(1L);
        when(eventService.getEventById(anyLong())).thenReturn(mock(Event.class));

        //THEN
        verify(eventService).getEventById(anyLong());
    }

    @Test
    void testGetEventsByTitle() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        ArrayList<Event> eventList = new ArrayList<>();
        UserServiceImpl userService = new UserServiceImpl();

        //WHEN
        when(eventService.getEventsByTitle(any(), anyInt(), anyInt())).thenReturn(eventList);
        List<Event> actualEventsByTitle = (new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService))
                .getEventsByTitle("title", 1, 1);

        //THEN
        assertSame(eventList, actualEventsByTitle);
        assertTrue(actualEventsByTitle.isEmpty());
        verify(eventService).getEventsByTitle(any(), anyInt(), anyInt());
    }

    @Test
    void testGetEventsForDay() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        ArrayList<Event> eventList = new ArrayList<>();
        UserServiceImpl userService = new UserServiceImpl();
        BookingFacadeImpl bookingFacadeImpl = new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();

        //WHEN
        when(eventService.getEventsForDay(any(), anyInt(), anyInt())).thenReturn(eventList);
        List<Event> actualEventsForDay = bookingFacadeImpl
                .getEventsForDay(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()), 1, 1);

        //THEN
        assertSame(eventList, actualEventsForDay);
        assertTrue(actualEventsForDay.isEmpty());
        verify(eventService).getEventsForDay(any(), anyInt(), anyInt());
    }

    @Test
    void testCreateEvent() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        UserServiceImpl userService = new UserServiceImpl();
        Event expectedEvent = getEvent();

        //WHEN
        when(eventService.createEvent(any())).thenReturn(expectedEvent);
        Event createdEvent = (new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService)).createEvent(expectedEvent);

        //THEN
        assertEquals(expectedEvent, createdEvent);
        verify(eventService).createEvent(any());
    }

    @Test
    void testUpdateEvent() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        UserServiceImpl userService = new UserServiceImpl();

        //WHEN
        when(eventService.updateEvent(any())).thenReturn(mock(Event.class));

        //THEN
        (new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService)).updateEvent(mock(Event.class));
        verify(eventService).updateEvent(any());
    }

    @Test
    void testDeleteEvent() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        UserServiceImpl userService = new UserServiceImpl();

        //WHEN
        when(eventService.deleteEvent(anyLong())).thenReturn(true);

        //THEN
        assertTrue((new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService)).deleteEvent(1L));
        verify(eventService).deleteEvent(anyLong());
    }

    @Test
    void testGetUserById() {
        //GIVEN
        UserService userService = mock(UserService.class);
        TicketServiceImpl ticketService = new TicketServiceImpl();

        //WHEN
        when(userService.getUserById(anyLong())).thenReturn(mock(User.class));
        (new BookingFacadeImpl(userService, ticketService, new EventServiceImpl())).getUserById(1L);

        //THEN
        verify(userService).getUserById(anyLong());
    }

    @Test
    void testGetUserByEmail() {
        //GIVEN
        UserService userService = mock(UserService.class);
        TicketServiceImpl ticketService = new TicketServiceImpl();

        //WHEN
        when(userService.getUserByEmail(any())).thenReturn(mock(User.class));
        (new BookingFacadeImpl(userService, ticketService, new EventServiceImpl())).getUserByEmail("email@example.com");

        //THEN
        verify(userService).getUserByEmail(any());
    }

    @Test
    void testGetUsersByName() {
        //GIVEN
        UserService userService = mock(UserService.class);
        ArrayList<User> userList = new ArrayList<>();
        TicketServiceImpl ticketService = new TicketServiceImpl();

        //WHEN
        when(userService.getUsersByName(any(), anyInt(), anyInt())).thenReturn(userList);
        List<User> actualUsersByName = (new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()))
                .getUsersByName("Name", 1, 1);

        //THEN
        assertSame(userList, actualUsersByName);
        assertTrue(actualUsersByName.isEmpty());
        verify(userService).getUsersByName(any(), anyInt(), anyInt());
    }

    @Test
    void testCreateUser() {
        //GIVEN
        UserService userService = mock(UserService.class);
        TicketServiceImpl ticketService = new TicketServiceImpl();

        //WHEN
        (new BookingFacadeImpl(userService, ticketService, new EventServiceImpl())).createUser(mock(User.class));
        when(userService.createUser(any())).thenReturn(mock(User.class));

        //THEN
        verify(userService).createUser(any());
    }

    @Test
    void testUpdateUser() {
        //GIVEN
        UserService userService = mock(UserService.class);
        TicketServiceImpl ticketService = new TicketServiceImpl();

        //WHEN
        when(userService.updateUser(any())).thenReturn(mock(User.class));
        (new BookingFacadeImpl(userService, ticketService, new EventServiceImpl())).updateUser(mock(User.class));

        //THEN
        verify(userService).updateUser(any());
    }

    @Test
    void testDeleteUser() {
        //GIVEN
        UserService userService = mock(UserService.class);
        TicketServiceImpl ticketService = new TicketServiceImpl();

        //WHEN
        when(userService.deleteUser(anyLong())).thenReturn(true);

        //THEN
        assertTrue((new BookingFacadeImpl(userService, ticketService, new EventServiceImpl())).deleteUser(1L));
        verify(userService).deleteUser(anyLong());
    }

    @Test
    void testBookTicket() {
        //GIVEN
        TicketService ticketService = mock(TicketService.class);
        UserServiceImpl userService = new UserServiceImpl();

        //WHEN
        when(ticketService.bookTicket(anyLong(), anyLong(), anyInt(), any()))
                .thenReturn(mock(Ticket.class));
        (new BookingFacadeImpl(userService, ticketService, new EventServiceImpl())).bookTicket(1L, 1L, 1,
                Ticket.Category.STANDARD);

        //THEN
        verify(ticketService).bookTicket(anyLong(), anyLong(), anyInt(), any());
    }

    @Test
    void testGetBookedTickets() {
        //GIVEN
        TicketService ticketService = mock(TicketService.class);
        ArrayList<Ticket> ticketList = new ArrayList<>();
        UserServiceImpl userService = new UserServiceImpl();

        //WHEN
        when(ticketService.getBookedTickets((Event) any(), anyInt(), anyInt())).thenReturn(ticketList);
        List<Ticket> actualBookedTickets = (new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()))
                .getBookedTickets(mock(Event.class), 1, 1);

        //THEN
        assertSame(ticketList, actualBookedTickets);
        assertTrue(actualBookedTickets.isEmpty());
        verify(ticketService).getBookedTickets((Event) any(), anyInt(), anyInt());
    }

    @Test
    void testCancelTicket() {
        //GIVEN
        TicketService ticketService = mock(TicketService.class);
        UserServiceImpl userService = new UserServiceImpl();

        //WHEN
        when(ticketService.cancelTicket(anyLong())).thenReturn(true);

        //THEN
        assertTrue((new BookingFacadeImpl(userService, ticketService, new EventServiceImpl())).cancelTicket(123L));
        verify(ticketService).cancelTicket(anyLong());
    }

    private Event getEvent() {
        final Event event = new EventImpl();
        event.setId(1L);
        event.setTitle("title");
        event.setDate(new Date());
        return event;
    }
}

