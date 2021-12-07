package com.epam.ticket.model.impl;

import com.epam.ticket.model.api.Ticket;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ticket")
public class TicketImpl implements Ticket {

    @Id
    @SequenceGenerator(
            name="ticket_id_seq",
            sequenceName="ticket_id_seq",
            allocationSize=1)
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator="ticket_id_seq"
    )
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "eventimpl_id", referencedColumnName = "id", nullable = false)
    private EventImpl event;

    @ManyToOne
    @JoinColumn(name = "userimpl_id", referencedColumnName = "id", nullable = false)
    private UserImpl user;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
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

    @Override
    public EventImpl getEvent() {
        return event;
    }

    public long getEventId() {
        return this.event.getId();
    }

    public void setEvent(EventImpl event) {
        this.event = event;
    }

    @Override
    public UserImpl getUser() {
        return user;
    }

    public long getUserId() {
        return this.user.getId();
    }

    public void setUser(UserImpl user) {
        this.user = user;
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
        return id == ticket.id && place == ticket.place && Objects.equals(event, ticket.event) && Objects.equals(user, ticket.user) && category == ticket.category;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, event, user, category, place);
    }

    @Override
    public String toString() {
        return "TicketImpl{" +
                "id=" + id +
                ", event=" + event +
                ", user=" + user +
                ", category=" + category +
                ", place=" + place +
                '}';
    }
}
