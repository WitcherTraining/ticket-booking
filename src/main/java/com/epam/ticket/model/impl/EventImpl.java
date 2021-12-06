package com.epam.ticket.model.impl;

import com.epam.ticket.model.api.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "eventimpl")
public class EventImpl implements Event {

    @Id
    @Column(name = "id")
    @SequenceGenerator(
            name="eventimpl_id_seq",
            sequenceName="event_id_seq",
            allocationSize=1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="eventimpl_id_seq"
    )
    private long Id;

    @Column(name = "title")
    private String title;

    @Column(name = "date")
    private Date date;

    @Column(name = "ticket_price")
    private int ticketPrice;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    List<TicketImpl> tickets = new ArrayList<>();

    public List<TicketImpl> getTickets() {
        return tickets;
    }

    public void setTickets(List<TicketImpl> tickets) {
        this.tickets = tickets;
    }

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

    public int getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(int ticketPrice) {
        this.ticketPrice = ticketPrice;
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
