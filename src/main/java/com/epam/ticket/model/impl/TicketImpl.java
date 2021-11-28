package com.epam.ticket.model.impl;

import com.epam.ticket.model.api.Ticket;

import java.util.Objects;

public class TicketImpl implements Ticket {

    private long id;
    private long eventId;
    private long userId;
    private Category category;
    private int place;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
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
    public String toString() {
        return "TicketImpl{" +
               "id=" + id +
               ", eventId=" + eventId +
               ", userId=" + userId +
               ", category=" + category +
               ", place=" + place +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketImpl ticket = (TicketImpl) o;
        return id == ticket.id && eventId == ticket.eventId && userId == ticket.userId && place == ticket.place && category == ticket.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, eventId, userId, category, place);
    }
}
