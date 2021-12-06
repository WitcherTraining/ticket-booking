package com.epam.ticket.model.api;

import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.model.impl.UserImpl;

import java.io.Serializable;

/**
 * Created by maksym_govorischev.
 */
public interface Ticket extends Serializable {
    public enum Category {STANDARD, PREMIUM, BAR}

    /**
     * Ticket Id. UNIQUE.
     *
     * @return Ticket Id.
     */
    long getId();

    void setId(long id);

    Event getEvent();

    User getUser();

    String getCategory();

    void setCategory(String category);

    int getPlace();

    void setPlace(int place);

}
