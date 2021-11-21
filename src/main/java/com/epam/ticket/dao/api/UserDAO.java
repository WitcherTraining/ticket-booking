package com.epam.ticket.dao.api;

import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.User;

public interface UserDAO extends BaseDAO<User> {

    User getUserByEmail(String email) throws EntityNotFoundException;

}
