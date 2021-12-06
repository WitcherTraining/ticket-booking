package com.epam.ticket.dao;

import com.epam.ticket.model.impl.TicketImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketDAO extends JpaRepository<TicketImpl, Long> {

}
