package com.epam.ticket.model.api;


import com.epam.ticket.model.impl.UserImpl;

public interface UserAccount {

    long getId();

    void setId(long id);

    int getPrepaidMoney();

    void setPrepaidMoney(int money);

    UserImpl getUser();

    void setUser(UserImpl userImpl);
}
