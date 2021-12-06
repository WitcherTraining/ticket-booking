package com.epam.ticket.dao;

import com.epam.ticket.model.impl.UserAccountImpl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountDAO extends JpaRepository<UserAccountImpl, Long> {

}
