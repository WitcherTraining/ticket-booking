package com.epam.ticket.model.api;

import com.epam.ticket.model.impl.EventImpl;
import com.epam.ticket.model.impl.UserImpl;

/**
 * Created by maksym_govorischev.
 */
public interface Ticket {
    public enum Category {STANDARD, PREMIUM, BAR}

    /**
     * Ticket Id. UNIQUE.
     *
     * @return Ticket Id.
     */
    long getId();

    void setId(long id);

    EventImpl getEventImpl();

    void setEventImpl(EventImpl eventImpl);

    UserImpl getUserImpl();

    void setUserImpl(UserImpl userImpl);

    String getCategory();

    void setCategory(String category);

    int getPlace();

    void setPlace(int place);

}
