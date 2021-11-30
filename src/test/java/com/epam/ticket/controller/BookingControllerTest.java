package com.epam.ticket.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.facade.impl.BookingFacadeImpl;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;
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

import org.junit.jupiter.api.Test;
import org.springframework.ui.ConcurrentModel;

class BookingControllerTest {

    public static final String EVENTS_EVENT = "events/event";
    public static final String EVENTS_EVENTS = "events/events";

    @Test
    void testGetIndex() {
        //GIVEN
        UserServiceImpl userService = new UserServiceImpl();
        TicketServiceImpl ticketService = new TicketServiceImpl();

        //THEN
        assertTrue((new BookingController(new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()))).getIndex().isReference());
    }

    @Test
    void testGetEventById() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        when(eventService.getEventById(anyLong())).thenReturn(mock(Event.class));
        UserServiceImpl userService = new UserServiceImpl();
        BookingController bookingController = new BookingController(
                new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService));

        //THEN
        assertEquals(EVENTS_EVENT, bookingController.getEventById(123L, new ConcurrentModel()));
        verify(eventService).getEventById(anyLong());
    }

    @Test
    void testGetEventsByTitle() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        when(eventService.getEventsByTitle(any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        UserServiceImpl userService = new UserServiceImpl();
        BookingController bookingController = new BookingController(
                new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService));

        //THEN
        assertEquals(EVENTS_EVENTS, bookingController.getEventsByTitle("Dr", new ConcurrentModel()));
        verify(eventService).getEventsByTitle(any(), anyInt(), anyInt());
    }

    @Test
    void testGetEventsForDay() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        when(eventService.getEventsForDay(any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        UserServiceImpl userService = new UserServiceImpl();
        BookingController bookingController = new BookingController(
                new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date day = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());

        //THEN
        assertEquals(EVENTS_EVENTS, bookingController.getEventsForDay(day, new ConcurrentModel()));
        verify(eventService).getEventsForDay(any(), anyInt(), anyInt());
    }

    @Test
    void testNewEvent() {
        //GIVEN
        UserServiceImpl userService = new UserServiceImpl();
        TicketServiceImpl ticketService = new TicketServiceImpl();
        BookingController bookingController = new BookingController(
                new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()));

        //THEN
        assertEquals("events/newEvent", bookingController.newEvent(new ConcurrentModel()));
    }

    @Test
    void testCreateEvent() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        when(eventService.createEvent(any())).thenReturn(mock(Event.class));
        UserServiceImpl userService = new UserServiceImpl();
        BookingController bookingController = new BookingController(
                new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date date = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());

        //THEN
        assertEquals("redirect:/api/event/1", bookingController.createEvent(1, "Dr", date, new ConcurrentModel()));
        verify(eventService).createEvent(any());
    }

    @Test
    void testUpdateEvent() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        when(eventService.updateEvent(any())).thenReturn(mock(Event.class));
        UserServiceImpl userService = new UserServiceImpl();
        BookingController bookingController = new BookingController(
                new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        Date date = Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant());

        //THEN
        assertEquals("redirect:/api/event/1", bookingController.updateEvent(1, "Dr", date, new ConcurrentModel()));
        verify(eventService).updateEvent(any());
    }

    @Test
    void testDeleteEvent() {
        //GIVEN
        EventService eventService = mock(EventService.class);
        when(eventService.deleteEvent(anyLong())).thenReturn(true);
        UserServiceImpl userService = new UserServiceImpl();

        //THEN
        assertEquals("redirect:/api/", (new BookingController(new BookingFacadeImpl(userService, new TicketServiceImpl(), eventService)))
                        .deleteEvent(123L));
        verify(eventService).deleteEvent(anyLong());
    }

    @Test
    void testGetUserById() {
        //GIVEN
        UserService userService = mock(UserService.class);
        when(userService.getUserById(anyLong())).thenReturn(mock(User.class));
        TicketServiceImpl ticketService = new TicketServiceImpl();
        BookingController bookingController = new BookingController(new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()));

        //THEN
        assertEquals("users/user", bookingController.getUserById(123, new ConcurrentModel()));
        verify(userService).getUserById(anyLong());
    }

    @Test
    void testGetUserByEmail() {
        //GIVEN
        UserService userService = mock(UserService.class);
        when(userService.getUserByEmail(any())).thenReturn(mock(User.class));
        TicketServiceImpl ticketService = new TicketServiceImpl();
        BookingController bookingController = new BookingController(new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()));

        //THEN
        assertEquals("users/user", bookingController.getUserByEmail("jane.doe@example.org", new ConcurrentModel()));
        verify(userService).getUserByEmail(any());
    }

    @Test
    void testGetUsersByName() {
        //GIVEN
        UserService userService = mock(UserService.class);
        when(userService.getUsersByName(any(), anyInt(), anyInt())).thenReturn(new ArrayList<>());
        TicketServiceImpl ticketService = new TicketServiceImpl();
        BookingController bookingController = new BookingController(new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()));

        //THEN
        assertEquals("users/users", bookingController.getUsersByName("Name", new ConcurrentModel()));
        verify(userService).getUsersByName(any(), anyInt(), anyInt());
    }

    @Test
    void testNewUser() {
        //GIVEN
        UserServiceImpl userService = new UserServiceImpl();
        TicketServiceImpl ticketService = new TicketServiceImpl();
        BookingController bookingController = new BookingController(new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()));

        //THEN
        assertEquals("users/newUser", bookingController.newUser(new ConcurrentModel()));
    }

    @Test
    void testCreateUser() {
        //GIVEN
        UserService userService = mock(UserService.class);
        when(userService.createUser(any())).thenReturn(mock(User.class));
        TicketServiceImpl ticketService = new TicketServiceImpl();
        BookingController bookingController = new BookingController(
                new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()));

        //THEN
        assertEquals("redirect:/api/user/1",
                bookingController.createUser(1, "Name", "jane.doe@example.org", new ConcurrentModel()));
        verify(userService).createUser(any());
    }

    @Test
    void testUpdateUser() {
        //GIVEN
        UserService userService = mock(UserService.class);
        when(userService.updateUser(any())).thenReturn(mock(User.class));
        TicketServiceImpl ticketService = new TicketServiceImpl();
        BookingController bookingController = new BookingController(
                new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()));

        //THEN
        assertEquals("redirect:/api/user/1",
                bookingController.updateUser(1, "Name", "jane.doe@example.org", new ConcurrentModel()));
        verify(userService).updateUser((User) any());
    }

    @Test
    void testDeleteUser() {
        //GIVEN
        UserService userService = mock(UserService.class);
        when(userService.deleteUser(anyLong())).thenReturn(true);
        TicketServiceImpl ticketService = new TicketServiceImpl();

        //THEN
        assertEquals("redirect:/api/",
                (new BookingController(new BookingFacadeImpl(userService, ticketService, new EventServiceImpl())))
                        .deleteUser(123L));
        verify(userService).deleteUser(anyLong());
    }

    @Test
    void testBookTicket() {
        //GIVEN
        TicketService ticketService = mock(TicketService.class);
        when(ticketService.bookTicket(anyLong(), anyLong(), anyInt(), any()))
                .thenReturn(mock(Ticket.class));
        UserServiceImpl userService = new UserServiceImpl();
        (new BookingController(new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()))).bookTicket(123L,
                123L, 1, Ticket.Category.STANDARD);

        //THEN
        verify(ticketService).bookTicket(anyLong(), anyLong(), anyInt(), any());
    }

    @Test
    void testCancelTicket() {
        //GIVEN
        TicketService ticketService = mock(TicketService.class);
        when(ticketService.cancelTicket(anyLong())).thenReturn(true);
        UserServiceImpl userService = new UserServiceImpl();
        (new BookingController(new BookingFacadeImpl(userService, ticketService, new EventServiceImpl()))).cancelTicket(123);

        //THEN
        verify(ticketService).cancelTicket(anyLong());
    }
}

