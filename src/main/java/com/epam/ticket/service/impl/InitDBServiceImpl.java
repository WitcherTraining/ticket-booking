package com.epam.ticket.service.impl;

import com.epam.ticket.InitialData;
import com.epam.ticket.dao.*;
import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.model.impl.TicketImpl;
import com.epam.ticket.model.impl.UserAccountImpl;
import com.epam.ticket.model.impl.UserImpl;
import com.epam.ticket.service.api.InitDBService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InitDBServiceImpl implements InitDBService {

    private static final Logger LOGGER = Logger.getLogger(InitDBServiceImpl.class);
    private static final String FILE_NAME = "src/main/resources/tickets.xml";
    public static final String DATE_PATTERN = "MM/dd/yyyy HH:mm";

    private final TicketDAO ticketDao;
    private final UserDAO userDAO;
    private final UserAccountDAO userAccountDAO;
    private final EventDAO eventDAO;
    private final XmlUnmarshaller xmlUnmarshaller;

    public InitDBServiceImpl(TicketDAO ticketDao, UserDAO userDAO, UserAccountDAO userAccountDAO, EventDAO eventDAO, XmlUnmarshaller xmlUnmarshaller) {
        this.ticketDao = ticketDao;
        this.userDAO = userDAO;
        this.userAccountDAO = userAccountDAO;
        this.eventDAO = eventDAO;
        this.xmlUnmarshaller = xmlUnmarshaller;
    }

    public void saveInitialDataInDB(InitialData initialData) throws IOException {

        LOGGER.info("Initialize Database...");

        final List<UserImpl> usersFromProperties = this.getUsersFromProperties(initialData.getUserIds(),
                initialData.getUserNames(),
                initialData.getUserEmails());

        final List<UserAccountImpl> userAccountsFromProperties = this.getUserAccountsFromProperties(initialData.getAccountIds(),
                initialData.getAccountMoney());

        final List<EventImpl> eventsFromProperties = this.getEventsFromProperties(initialData.getEventIds(),
                initialData.getEventTitles(),
                initialData.getEventDates(),
                initialData.getEventPrices());

        final List<TicketImpl> ticketsFromProperties = this.getTicketsFromProperties(initialData.getTicketIds(),
                initialData.getEventIds(),
                initialData.getUserIds(),
                initialData.getTicketCategories(),
                initialData.getTicketPlaces());
        ticketsFromProperties.addAll(this.xmlUnmarshaller.preLoadTickets(FILE_NAME));

        this.prefillDB(usersFromProperties, userAccountsFromProperties, eventsFromProperties, ticketsFromProperties);
    }

    private void prefillDB(List<UserImpl> usersFromProperties, List<UserAccountImpl> userAccountsFromProperties,
                           List<EventImpl> eventsFromProperties, List<TicketImpl> ticketsFromProperties) {
        this.eventDAO.saveAll(eventsFromProperties);
        this.userDAO.saveAll(usersFromProperties);
        this.userAccountDAO.saveAll(userAccountsFromProperties);
        this.ticketDao.saveAll(ticketsFromProperties);
    }

    private List<UserImpl> getUsersFromProperties(String rawIds, String rawNames, String rawEmails) {
        List<UserImpl> usersToReturn = new ArrayList<>();
        List<String> userIds = this.splitValuesFromInitFile(rawIds);
        List<String> names = this.splitValuesFromInitFile(rawNames);
        List<String> emails = this.splitValuesFromInitFile(rawEmails);

        for (int i = 0; i < userIds.size(); i++) {
            UserImpl user = new UserImpl();
            user.setId(Long.parseLong(userIds.get(i)));
            user.setName(names.get(i));
            user.setEmail(emails.get(i));
            usersToReturn.add(user);
        }

        return usersToReturn;
    }

    private List<UserAccountImpl> getUserAccountsFromProperties(String rawIds, String rawPrepaidMoney) {
        List<UserAccountImpl> userAccountsToReturn = new ArrayList<>();
        List<String> userIds = this.splitValuesFromInitFile(rawIds);
        List<String> prepaidMoney = this.splitValuesFromInitFile(rawPrepaidMoney);

        for (int i = 0; i < userIds.size(); i++) {
            UserAccountImpl userAccount = new UserAccountImpl();
            userAccount.setId(Long.parseLong(userIds.get(i)));
            userAccount.setPrepaidMoney(Integer.parseInt(prepaidMoney.get(i)));
            userAccount.setUser(this.userDAO.getById(Long.parseLong(userIds.get(i))));
            userAccountsToReturn.add(userAccount);
        }

        return userAccountsToReturn;
    }

    private List<EventImpl> getEventsFromProperties(String rawIds, String rawTitles, String rawDates, String rawPrices) {
        List<EventImpl> eventsToReturn = new ArrayList<>();
        List<String> ids = this.splitValuesFromInitFile(rawIds);
        List<String> titles = this.splitValuesFromInitFile(rawTitles);
        List<String> dates = this.splitValuesFromInitFile(rawDates);
        List<String> prices = this.splitValuesFromInitFile(rawPrices);

        for (int i = 0; i < ids.size(); i++) {
            EventImpl event = new EventImpl();
            event.setId(Long.parseLong(ids.get(i)));
            event.setTitle(titles.get(i));
            event.setTicketPrice(Integer.parseInt(prices.get(i)));
            try {
                event.setDate(new SimpleDateFormat(DATE_PATTERN).parse(dates.get(i)));
            } catch (ParseException e) {
                LOGGER.error(String.format("Cannot parse initial date from this string: [%s]", dates.get(i)), e);
            }
            eventsToReturn.add(event);
        }

        return eventsToReturn;
    }

    private List<TicketImpl> getTicketsFromProperties(String rawIds, String rawEventIds, String rawUserIds, String rawCategories, String rawPlaces) {
        List<TicketImpl> ticketsToReturn = new ArrayList<>();
        List<String> ids = this.splitValuesFromInitFile(rawIds);
        List<String> eventIds = this.splitValuesFromInitFile(rawEventIds);
        List<String> userIds = this.splitValuesFromInitFile(rawUserIds);
        List<String> categories = this.splitValuesFromInitFile(rawCategories);
        List<String> places = this.splitValuesFromInitFile(rawPlaces);

        for (int i = 0; i < ids.size(); i++) {
            TicketImpl ticket = new TicketImpl();
            ticket.setId(Long.parseLong(ids.get(i)));
            ticket.setEvent(this.eventDAO.getById(Long.parseLong(eventIds.get(i))));
            ticket.setUser(this.userDAO.getById(Long.parseLong(userIds.get(i))));
            ticket.setCategory(categories.get(i));
            ticket.setPlace(Integer.parseInt(places.get(i)));

            ticketsToReturn.add(ticket);
        }

        return ticketsToReturn;
    }

    private List<String> splitValuesFromInitFile(String rawValues) {
        return Arrays.stream(rawValues.split(",")).collect(Collectors.toList());
    }
}
