package com.epam.ticket.integrationtests.user.workflow;

import com.epam.ticket.facade.api.BookingFacade;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.model.impl.TicketImpl;
import com.epam.ticket.model.impl.UserImpl;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class UserStandardWorkflowSteps {

    private final ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
    private final BookingFacade bookingFacade = context.getBean(BookingFacade.class);

    @When("I trying to create a new user with id {long} and name {string} and email {string}")
    public void iTryingToCreateANewUserWithIdAndNameAndEmail(Long id, String name, String email) {

        final User user = new UserImpl();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);

        this.bookingFacade.createUser(user);
    }

    @Then("The following information is received for the new user:")
    public void theFollowingInformationIsReceivedForTheNewUser(DataTable rawUser) {
        List<List<String>> data = rawUser.asLists(String.class);

        final User expectedUser = new UserImpl();
        expectedUser.setId(Long.parseLong(data.get(1).get(0)));
        expectedUser.setName(data.get(1).get(1));
        expectedUser.setEmail(data.get(1).get(2));

        final User actualIfExist = this.bookingFacade.getUserById(expectedUser.getId());
        Assert.assertEquals(actualIfExist, expectedUser);
    }

    @When("I trying to create new event with id {long} and title {string} and date {string}")
    public void iTryingToCreateNewEventWithIdAndTitleAndDate(Long id, String title, String date) throws ParseException {

        final Event event = new EventImpl();
        event.setId(id);
        event.setTitle(title);
        event.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date));

        this.bookingFacade.createEvent(event);
    }

    @Then("The following information is received for the new event:")
    public void iWillBeAbleToFindNewEvent(DataTable rawEvent) throws ParseException {
        List<List<String>> data = rawEvent.asLists(String.class);

        final Event expectedEvent = new EventImpl();
        expectedEvent.setId(Long.parseLong(data.get(1).get(0)));
        expectedEvent.setTitle(data.get(1).get(1));
        expectedEvent.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(data.get(1).get(2)));

        final Event actualIfExist = this.bookingFacade.getEventById(expectedEvent.getId());
        Assert.assertEquals(actualIfExist, expectedEvent);
    }

    @Given("User with ID {long} and event with ID {long}")
    public void defineUserAndEvent(Long userId, Long eventId) {

        final User user = new UserImpl();
        user.setId(userId);
        final Event event = new EventImpl();
        event.setId(eventId);

        this.bookingFacade.createUser(user);
        this.bookingFacade.createEvent(event);
    }

    @When("I trying to book the ticket with user ID {long} and event ID {long} and place number {int} and ticket category {string}")
    public void iTryingToBookTheTicketWithUserIDAndEventIDAndPlaceNumberAndTicketCategory(Long userId, Long eventId, Integer place, String category) {
        this.bookingFacade.bookTicket(userId, eventId, place, Ticket.Category.valueOf(category));
    }

    @Then("The following information is received for the ticket booked by this user with ID {long}:")
    public void iWillBeAbleToFindMyTicket(Long userId, DataTable rawTicket) {
        final User expectedUser = this.bookingFacade.getUserById(userId);
        List<List<String>> data = rawTicket.asLists(String.class);

        final Ticket expectedTicket = new TicketImpl();
        expectedTicket.setId(Long.parseLong(data.get(1).get(0)));
//        expectedTicket.setUserId(Long.parseLong(data.get(1).get(1)));
//        expectedTicket.setEventId(Long.parseLong(data.get(1).get(2)));
        expectedTicket.setPlace(Integer.parseInt(data.get(1).get(3)));
        expectedTicket.setCategory(data.get(1).get(4));

        final List<Ticket> actualBookedTickets = this.bookingFacade.getBookedTickets(expectedUser, 1, 1);

        Assert.assertEquals(actualBookedTickets.get(0), expectedTicket);
    }

    @Given("Ticket with user ID {long} and event ID {long} and place number {int} and ticket category {string}")
    public void defineMockTicket(Long userId, Long eventId, Integer place, String category) {
        final User user = new UserImpl();
        user.setId(userId);
        final Event event = new EventImpl();
        event.setId(eventId);

        this.bookingFacade.createUser(user);
        this.bookingFacade.createEvent(event);
        this.bookingFacade.bookTicket(userId, eventId, place, Ticket.Category.valueOf(category));
    }

    @Then("I cancel my ticket with ID {long}")
    public void iCancelMyTicket(Long id) {
        Assert.assertTrue(this.bookingFacade.cancelTicket(id));
    }
}
