package com.epam.ticket.dao;

import com.epam.ticket.model.impl.EventImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDAO extends JpaRepository<EventImpl, Long> {

}
