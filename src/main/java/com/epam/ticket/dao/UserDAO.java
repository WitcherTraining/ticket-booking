package com.epam.ticket.dao;

import com.epam.ticket.model.impl.UserImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<UserImpl, Long> {

}
