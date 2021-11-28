package com.epam.ticket.controller;

import com.epam.ticket.facade.api.BookingFacade;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.model.impl.UserImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/api")
public class BookingController {

    private BookingFacade bookingFacade;

    public BookingController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @RequestMapping("/")
    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }


    @GetMapping("/event/{eventId}")
    public String getEventById(@PathVariable long eventId, Model model) {
        model.addAttribute("event", this.bookingFacade.getEventById(eventId));
        return "events/event";
    }

    @GetMapping("/eventsByTitle")
    public String getEventsByTitle(@RequestParam String title, Model model) {
        model.addAttribute("events", this.bookingFacade.getEventsByTitle(title, 1, 1));
        return "events/events";
    }

    @GetMapping("/eventsByDay")
    public String getEventsForDay(@RequestParam Date day, Model model) {
        model.addAttribute("events", this.bookingFacade.getEventsForDay(day, 1, 1));
        return "events/events";
    }

    @GetMapping("/newEvent")
    public String newEvent(Model model) {
        model.addAttribute("event", new EventImpl());
        return "events/newEvent";
    }

    @PostMapping("/event")
    public String createEvent(@RequestParam int id, @RequestParam String title, @RequestParam Date date, Model model) {
        model.addAttribute("event", new EventImpl());
        final Event newEvent = new EventImpl();
        newEvent.setId(id);
        newEvent.setTitle(title);
        newEvent.setDate(date);
        this.bookingFacade.createEvent(newEvent);
        return "redirect:/api/event/" + newEvent.getId();
    }

    @PatchMapping("/updateEvent")
    public String updateEvent(@RequestParam int id, @RequestParam String title, @RequestParam Date date, Model model) {
        model.addAttribute("event", new EventImpl());
        final Event event = new EventImpl();
        event.setId(id);
        event.setTitle(title);
        event.setDate(date);
        this.bookingFacade.updateEvent(event);
        return "redirect:/api/event/" + event.getId();
    }

    @DeleteMapping("/deleteEvent/{eventId}")
    public String deleteEvent(@PathVariable long eventId) {
        this.bookingFacade.deleteEvent(eventId);
        return "redirect:/api/";
    }

    @GetMapping("/user/{userId}")
    public String getUserById(@PathVariable int userId, Model model) {
        final User userById = this.bookingFacade.getUserById(userId);
        model.addAttribute("user", userById);
        return "users/user";
    }

    @GetMapping("/userByEmail")
    public String getUserByEmail(@RequestParam String email, Model model) {
        model.addAttribute("user", this.bookingFacade.getUserByEmail(email));
        return "users/user";
    }

    @GetMapping("/usersByName")
    public String getUsersByName(@RequestParam String name, Model model) {
        model.addAttribute("users", this.bookingFacade.getUsersByName(name, 1, 1));
        return "users/users";
    }

    @GetMapping("/newUser")
    public String newUser(Model model) {
        model.addAttribute("user", new UserImpl());
        return "users/newUser";
    }

    @PostMapping("/user")
    public String createUser(@RequestParam int id, @RequestParam String name, @RequestParam String email, Model model) {
        model.addAttribute("user", new UserImpl());
        final User user = new UserImpl();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        this.bookingFacade.createUser(user);
        return "redirect:/api/user/" + user.getId();
    }

    @PatchMapping("/updateUser")
    public String updateUser(@RequestParam int id, @RequestParam String name, @RequestParam String email, Model model) {
        model.addAttribute("user", new UserImpl());
        final User user = new UserImpl();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        this.bookingFacade.updateUser(user);
        return "redirect:/api/user/" + user.getId();
    }

    @DeleteMapping("/deleteUser/{userId}")
    public String deleteUser(@PathVariable long userId) {
        this.bookingFacade.deleteUser(userId);
        return "redirect:/api/";
    }

    @PostMapping("/ticket")
    public void bookTicket(@RequestParam long userId, @RequestParam long eventId, @RequestParam int place, @RequestParam Ticket.Category category) {
        this.bookingFacade.bookTicket(userId, eventId, place, category);
    }

    @DeleteMapping("/ticket")
    public void cancelTicket(@RequestParam int ticketId) {
        this.bookingFacade.cancelTicket(ticketId);
    }
}
