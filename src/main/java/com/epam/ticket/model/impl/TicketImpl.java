package com.epam.ticket.model.impl;

import com.epam.ticket.model.api.Ticket;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ticket")
public class TicketImpl implements Ticket {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false, referencedColumnName = "id")
    private EventImpl eventImpl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private UserImpl userImpl;

    @Column(name = "category")
    private Category category;

    @Column(name = "place")
    private int place;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public EventImpl getEventImpl() {
        return eventImpl;
    }

    public void setEventImpl(EventImpl eventImpl) {
        this.eventImpl = eventImpl;
    }

    @Override
    public UserImpl getUserImpl() {
        return userImpl;
    }

    @Override
    public void setUserImpl(UserImpl userImpl) {
        this.userImpl = userImpl;
    }

    @Override
    public String getCategory() {
        return String.valueOf(category);
    }

    @Override
    public void setCategory(String category) {
        this.category = Category.valueOf(category);
    }

    @Override
    public int getPlace() {
        return place;
    }

    @Override
    public void setPlace(int place) {
        this.place = place;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketImpl ticket = (TicketImpl) o;
        return id == ticket.id && place == ticket.place && Objects.equals(eventImpl, ticket.eventImpl) && Objects.equals(userImpl, ticket.userImpl) && category == ticket.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventImpl, userImpl, category, place);
    }

    @Override
    public String toString() {
        return "TicketImpl{" +
                "id=" + id +
                ", eventImpl=" + eventImpl +
                ", userImpl=" + userImpl +
                ", category=" + category +
                ", place=" + place +
                '}';
    }
}
