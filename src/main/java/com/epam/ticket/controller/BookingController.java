package com.epam.ticket.controller;

import com.epam.ticket.facade.api.BookingFacade;
import com.epam.ticket.model.api.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class BookingController {

    private final BookingFacade bookingFacade;

    public BookingController(BookingFacade bookingFacade) {
        this.bookingFacade = bookingFacade;
    }

    @RequestMapping("/")
    public ModelAndView root() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/user")
    public User getUser(@RequestParam int id) {
        return bookingFacade.getUserById(id);
    }
}
