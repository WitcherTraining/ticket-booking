package com.epam.ticket;

import com.epam.ticket.facade.api.BookingFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Runner {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        BookingFacade bookingFacade = context.getBean(BookingFacade.class);

        System.out.println(bookingFacade.getEventById(2));
    }

}
