package com.epam.ticket.model.api;


import com.epam.ticket.model.impl.UserImpl;

import java.io.Serializable;

public interface UserAccount extends Serializable {

    long getId();

    void setId(long id);

    int getPrepaidMoney();

    void setPrepaidMoney(int money);

    User getUser();

    void setUser(UserImpl user);
}
