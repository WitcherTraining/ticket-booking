package com.epam.ticket.service.impl;

import com.epam.ticket.dao.api.UserDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.impl.UserImpl;
import com.epam.ticket.service.api.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public User getUserById(long userId) {
        try {
            return this.userDAO.findOne(userId);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot find user with ID [%s]", userId), e);
        }
        return new UserImpl();
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            return this.userDAO.getUserByEmail(email);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot find user with email [%s]", email), e);
        }
        return new UserImpl();
    }

    @Override
    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        return this.userDAO.findAll()
                .stream()
                .filter(user -> user.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public User createUser(User user) {
        return this.userDAO.create(user);
    }

    @Override
    public User updateUser(User user) {
        try {
            return this.userDAO.update(user);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot find user [%s]", user), e);
        }
        return new UserImpl();
    }

    @Override
    public boolean deleteUser(long userId) {
        try {
            return this.userDAO.remove(userId);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot delete user with ID [%s]", userId), e);
        }
        return Boolean.FALSE;
    }
}
