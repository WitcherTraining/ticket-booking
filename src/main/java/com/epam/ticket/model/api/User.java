package com.epam.ticket.model.api;

import com.epam.ticket.model.impl.TicketImpl;

import java.io.Serializable;
import java.util.List;

/**
 * Created by maksym_govorischev on 14/03/14.
 */
public interface User extends Serializable {
    /**
     * User Id. UNIQUE.
     * @return User Id.
     */
    long getId();
    void setId(long id);
    String getName();
    void setName(String name);

    /**
     * User email. UNIQUE.
     * @return User email.
     */
    String getEmail();
    void setEmail(String email);
    List<TicketImpl> getTickets();
}
