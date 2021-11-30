package com.epam.ticket.integrationtests.controller;

import com.epam.ticket.config.SpringConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@WebAppConfiguration
public class BookingControllerMockMvcIT {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void givenWebContext_whenServletContext_thenItProvidesBookingController() {
        ServletContext servletContext = webApplicationContext.getServletContext();

        Assertions.assertNotNull(servletContext);
        Assertions.assertTrue(servletContext instanceof MockServletContext);
        Assertions.assertNotNull(webApplicationContext.getBean("bookingController"));
    }

    @Test
    public void givenHomePageURI_whenMockMVC_thenReturnsIndexHtmlViewName() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/")).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    public void givenEventURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/event/{eventId}", "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("events/event"));
    }

    @Test
    public void givenEventURIWithQueryParam_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/eventsByTitle").param("title", "concert"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("events/events"));
    }

    @Test
    public void givenEventURIWithQueryParamDate_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/eventsByDay").param("day", "07/06/2021 10:00"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("events/events"));
    }

    @Test
    public void givenEventURIWithPost_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/event")
                        .param("id", "1").param("title", "concert").param("date", "07/06/2021 10:00"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/api/event/1"));
    }

    @Test
    public void givenEventURIWithPatch_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/updateEvent")
                        .param("id", "1").param("title", "concert").param("date", "07/06/2021 10:00"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/api/event/1"));
    }

    @Test
    public void givenEventURIWithDelete_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/deleteEvent/{eventId}", 1))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/api/"));
    }

    @Test
    public void givenUserURIWithPathVariable_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/user/{userId}", "1"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/user"));
    }

    @Test
    public void givenUserURIWithQueryParam_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/userByEmail").param("email", "lera@gmail.com"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/user"));
    }

    @Test
    public void givenUserURIWithQueryParamDate_whenMockMVC_thenResponseOK() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/usersByName").param("name", "Sara"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/users"));
    }

    @Test
    public void givenUserURIWithPost_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                        .param("id", "4").param("name", "Kirill").param("email", "kirill@gmail.com"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/api/user/4"));
    }

    @Test
    public void givenUserURIWithPatch_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.patch("/api/updateUser")
                        .param("id", "1").param("name", "Kirill").param("email", "kirill@gmail.com"))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/api/user/1"));
    }

    @Test
    public void givenUserURIWithDelete_whenMockMVC_thenVerifyResponse() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/deleteUser/{userId}", 1))
                .andDo(print()).andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/api/"));
    }
}
