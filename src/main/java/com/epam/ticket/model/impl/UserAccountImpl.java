package com.epam.ticket.model.impl;

import com.epam.ticket.model.api.UserAccount;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_account")
public class UserAccountImpl implements UserAccount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "prepaid_money")
    private int prepaidMoney;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserImpl user;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public int getPrepaidMoney() {
        return prepaidMoney;
    }

    @Override
    public void setPrepaidMoney(int prepaidMoney) {
        this.prepaidMoney = prepaidMoney;
    }

    public UserImpl getUser() {
        return user;
    }

    public void setUser(UserImpl user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccountImpl that = (UserAccountImpl) o;
        return id == that.id && prepaidMoney == that.prepaidMoney && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prepaidMoney, user);
    }

    @Override
    public String toString() {
        return "UserAccountImpl{" +
                "id=" + id +
                ", prepaidMoney=" + prepaidMoney +
                ", user=" + user +
                '}';
    }
}
