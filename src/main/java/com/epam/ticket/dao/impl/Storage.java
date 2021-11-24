package com.epam.ticket.dao.impl;

import com.epam.ticket.InitialData;
import com.epam.ticket.model.api.Event;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.model.impl.TicketImpl;
import com.epam.ticket.model.impl.UserImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class Storage {
    private static final Logger LOGGER = Logger.getLogger(Storage.class);

    private Map<Long, User> userMap = new ConcurrentHashMap<>();
    private Map<Long, Ticket> ticketMap = new ConcurrentHashMap<>();
    private Map<Long, Event> eventMap = new ConcurrentHashMap<>();
    private static Storage storage = null;
    private final List<Long> ids = new ArrayList<>(Arrays.asList(1L, 2L, 3L));

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public Storage init() {
        if (storage == null) {
            storage = new Storage();
        }
        final InitialData initialData = context.getBean(InitialData.class);

        LOGGER.info("Initialize storage...");
        System.out.println("Initialize storage...");

        final List<User> usersFromProperties = this.getUsersFromProperties(initialData.getUserIds(),
                initialData.getUserNames(),
                initialData.getUserEmails());

        final List<Event> eventsFromProperties = this.getEventsFromProperties(initialData.getEventIds(),
                initialData.getEventTitles(),
                initialData.getEventDates());

        final List<Ticket> ticketsFromProperties = this.getTicketsFromProperties(initialData.getTicketIds(),
                initialData.getEventIds(),
                initialData.getUserIds(),
                initialData.getTicketCategories(),
                initialData.getTicketPlaces());

        this.populateStorageMaps(usersFromProperties, eventsFromProperties, ticketsFromProperties);

        return storage;
    }

    public Map<Long, User> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<Long, User> userMap) {
        this.userMap = userMap;
    }

    public Map<Long, Ticket> getTicketMap() {
        return ticketMap;
    }

    public void setTicketMap(Map<Long, Ticket> ticketMap) {
        this.ticketMap = ticketMap;
    }

    public Map<Long, Event> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<Long, Event> eventMap) {
        this.eventMap = eventMap;
    }

    private void populateStorageMaps(List<User> users, List<Event> events, List<Ticket> tickets) {
        users.forEach(user -> this.userMap.put(user.getId(), user));
        events.forEach(event -> this.eventMap.put(event.getId(), event));
        tickets.forEach(ticket -> this.ticketMap.put(ticket.getId(), ticket));
    }

    private List<User> getUsersFromProperties(String rawIds, String rawNames, String rawEmails) {
        List<User> usersToReturn = new ArrayList<>();
        List<String> userIds = this.splitValuesFromInitFile(rawIds);
        List<String> names = this.splitValuesFromInitFile(rawNames);
        List<String> emails = this.splitValuesFromInitFile(rawEmails);

        for (int i = 0; i < userIds.size(); i++) {
            User user = new UserImpl();
            user.setId(this.ids.get(i));
            user.setName(names.get(i));
            user.setEmail(emails.get(i));
            usersToReturn.add(user);
        }

        return usersToReturn;
    }

    private List<Event> getEventsFromProperties(String rawIds, String rawTitles, String rawDates) {
        List<Event> eventsToReturn = new ArrayList<>();
        List<String> ids = this.splitValuesFromInitFile(rawIds);
        List<String> titles = this.splitValuesFromInitFile(rawTitles);
        List<String> dates = this.splitValuesFromInitFile(rawDates);

        for (int i = 0; i < ids.size(); i++) {
            Event event = new EventImpl();
            event.setId(this.ids.get(i));
            event.setTitle(titles.get(i));
//            try {
//                event.setDate(new SimpleDateFormat("dd/MM/yyyy HH:mm").parse(dates.get(i)));
            event.setDate(new Date());
//            } catch (ParseException e) {
//                LOGGER.error(String.format("Cannot parse initial date from this string: [%s]", dates.get(i)), e);
//            }
            eventsToReturn.add(event);
        }

        return eventsToReturn;
    }

    private List<Ticket> getTicketsFromProperties(String rawIds, String rawEventIds, String rawUserIds, String rawCategories, String rawPlaces) {
        List<Ticket> ticketsToReturn = new ArrayList<>();
        List<String> ids = this.splitValuesFromInitFile(rawIds);
        List<String> eventIds = this.splitValuesFromInitFile(rawEventIds);
        List<String> userIds = this.splitValuesFromInitFile(rawUserIds);
        List<String> categories = this.splitValuesFromInitFile(rawCategories);
        List<String> places = this.splitValuesFromInitFile(rawPlaces);

        for (int i = 0; i < ids.size(); i++) {
            Ticket ticket = new TicketImpl();
            ticket.setId(this.ids.get(i));
            ticket.setEventId(this.ids.get(i));
            ticket.setUserId(this.ids.get(i));
            ticket.setCategory(Ticket.Category.STANDARD);
            ticket.setPlace(Math.toIntExact(this.ids.get(i)));

            ticketsToReturn.add(ticket);
        }

        return ticketsToReturn;
    }

    private List<String> splitValuesFromInitFile(String rawValues) {
        return Arrays.stream(rawValues.split(",")).collect(Collectors.toList());
    }

}
