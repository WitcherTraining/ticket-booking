package com.epam.ticket.model.impl;

import com.epam.ticket.model.api.User;
import com.epam.ticket.model.api.UserAccount;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_account_impl")
public class UserAccountImpl implements UserAccount {

    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name="user_account_impl_id_seq",
            sequenceName="user_account_id_seq",
            allocationSize=1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="user_account_impl_id_seq"
    )
    private long id;

    @Column(name = "prepaid_money")
    private int prepaidMoney;

    @ManyToOne
    @JoinColumn(name = "eventimpl_id", referencedColumnName = "id", nullable = false)
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

    @Override
    public User getUser() {
        return user;
    }

    @Override
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
