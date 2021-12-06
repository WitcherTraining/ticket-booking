package com.epam.ticket.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.dao.UserDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.User;

import java.util.ArrayList;

import com.epam.ticket.model.impl.UserImpl;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class UserServiceImplTest {

    public static final String USER_DAO = "userDAO";

    @Test
    void testGetUserById() throws EntityNotFoundException {
        //GIVEN
        UserDAO userDAO = mock(UserDAO.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        //WHEN
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);
//        when(userDAO.findOne(anyLong())).thenReturn(mock(User.class));

        //THEN
        assertNotNull(userServiceImpl.getUserById(1L));
//        verify(userDAO).findOne(anyLong());
    }

    @Test
    void testGetUserByEmail() throws EntityNotFoundException {
        //GIVEN
        UserDAO userDAO = mock(UserDAO.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        //WHEN
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);
//        when(userDAO.getUserByEmail(anyString())).thenReturn(mock(User.class));

        //THEN
        assertNotNull(userServiceImpl.getUserByEmail("email@example.com"));
//        verify(userDAO).getUserByEmail(any());
    }

    @Test
    void testGetUsersByName() {
        //GIVEN
        User user = mock(User.class);
        when(user.getName()).thenReturn("name");
        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        UserDAO userDAO = mock(UserDAO.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        //WHEN
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);
//        when(userDAO.findAll()).thenReturn(userList);

        //THEN
        assertEquals(1, userServiceImpl.getUsersByName("name", 1, 1).size());
        verify(userDAO).findAll();
        verify(user).getName();
    }

    @Test
    void testCreateUser() {
        //GIVEN
        UserDAO userDAO = mock(UserDAO.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl();

        //WHEN
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);
//        when(userDAO.create(any())).thenReturn(mock(User.class));
        userServiceImpl.createUser(mock(User.class));

        //THEN
//        verify(userDAO).create(any());
    }

    @Test
    void testUpdateUser() throws EntityNotFoundException {
        //GIVEN
        UserDAO userDAO = mock(UserDAO.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        User user = getUser();
        User updatedUser = getUser();
        updatedUser.setName("test_name");

        //WHEN
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);
        final User actualUpdated = userServiceImpl.updateUser(updatedUser);

        //THEN
        assertNotEquals(actualUpdated, user);
//        verify(userDAO).update(any());
    }

    @Test
    void testDeleteUser() throws EntityNotFoundException {
        //GIVEN
        UserDAO userDAO = mock(UserDAO.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        User user = getUser();

        //WHEN
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);
//        when(userDAO.remove(user.getId())).thenReturn(true);

        //THEN
        assertTrue(userServiceImpl.deleteUser(1L));
//        verify(userDAO).remove(anyLong());
    }

    private User getUser() {
        final User user = new UserImpl();
        user.setId(1L);
        user.setName("Name");
        user.setEmail("email");
        return user;
    }
}

