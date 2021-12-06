package com.epam.ticket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:external-boot.properties")
public class InitialData {
    @Value("${user.ids}")
    String userIds;

    @Value("${user.names}")
    String userNames;

    @Value("${user.emails}")
    String userEmails;

    @Value("${event.ids}")
    String eventIds;

    @Value("${event.titles}")
    String eventTitles;

    @Value("${event.dates}")
    String eventDates;

    @Value("${event.prices}")
    String eventPrices;

    @Value("${ticket.ids}")
    String ticketIds;

    @Value("${ticket.eventIds}")
    String ticketEventIds;

    @Value("${ticket.userIds}")
    String ticketUserIds;

    @Value("${account.ids}")
    String accountIds;

    @Value("${account.money}")
    String accountMoney;

    @Value("${ticket.categories}")
    String ticketCategories;

    @Value("${ticket.places}")
    String ticketPlaces;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public String getUserIds() {
        return userIds;
    }

    public String getUserNames() {
        return userNames;
    }

    public String getUserEmails() {
        return userEmails;
    }

    public String getEventIds() {
        return eventIds;
    }

    public String getEventTitles() {
        return eventTitles;
    }

    public String getEventDates() {
        return eventDates;
    }

    public String getTicketIds() {
        return ticketIds;
    }

    public String getTicketEventIds() {
        return ticketEventIds;
    }

    public String getTicketUserIds() {
        return ticketUserIds;
    }

    public String getTicketCategories() {
        return ticketCategories;
    }

    public String getTicketPlaces() {
        return ticketPlaces;
    }

    public String getEventPrices() {
        return eventPrices;
    }

    public String getAccountIds() {
        return accountIds;
    }

    public String getAccountMoney() {
        return accountMoney;
    }
}
