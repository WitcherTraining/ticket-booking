package com.epam.ticket.model.impl;

import com.epam.ticket.model.api.Event;

import java.util.Date;
import java.util.Objects;

public class EventImpl implements Event {

    private long Id;
    private String title;
    private Date date;

    @Override
    public long getId() {
        return Id;
    }

    @Override
    public void setId(long id) {
        Id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "EventImpl{" +
                "Id=" + Id +
                ", title='" + title + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventImpl event = (EventImpl) o;
        return Id == event.Id && Objects.equals(title, event.title) && Objects.equals(date, event.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Id, title, date);
    }
}
