package com.epam.ticket.dao.impl;

import com.epam.ticket.dao.api.UserDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.impl.UserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private Storage storage;

    @Override
    public List<User> findAll() {
        return new ArrayList<>(this.storage.getUserMap().values());
    }

    @Override
    public User findOne(long id) throws EntityNotFoundException {
        User user = this.storage.getUserMap().get(id);
        if (Objects.nonNull(user)) {
            return user;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User update(User user) throws EntityNotFoundException {
        User userToUpdate = this.storage.getUserMap().get(user.getId());
        if (Objects.nonNull(userToUpdate)) {
            User updatedUser = this.mapUser(user, userToUpdate);
            storage.getUserMap().put(userToUpdate.getId(), updatedUser);
            return updatedUser;
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User create(User entity) {
        storage.getUserMap().put(entity.getId(), entity);
        return entity;
    }

    @Override
    public boolean remove(long id) throws EntityNotFoundException {
        User user = storage.getUserMap().get(id);
        if (Objects.nonNull(user)) {
            return storage.getUserMap().remove(id, user);
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    public User getUserByEmail(String email) throws EntityNotFoundException {
        return this.storage.getUserMap().values()
                .stream()
                .filter(user -> Objects.equals(user.getEmail(), email))
                .findFirst()
                .orElseThrow(EntityNotFoundException::new);
    }

    private User mapUser(User user, User userToUpdate) {
        final User updatedUser = new UserImpl();
        updatedUser.setId(userToUpdate.getId());
        updatedUser.setName(Objects.nonNull(user.getName()) ? user.getName() : userToUpdate.getName());
        updatedUser.setEmail(Objects.nonNull(user.getEmail()) ? user.getEmail() : userToUpdate.getEmail());
        return updatedUser;
    }
}
