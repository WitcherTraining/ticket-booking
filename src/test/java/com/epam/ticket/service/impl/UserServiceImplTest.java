package com.epam.ticket.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.dao.UserDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.User;
import com.epam.ticket.model.impl.TicketImpl;
import com.epam.ticket.model.impl.UserAccountImpl;

import java.util.ArrayList;

import com.epam.ticket.model.impl.UserImpl;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class UserServiceImplTest {

    public static final String USER_DAO = "userDAO";

    @Test
    void testGetUserById() throws EntityNotFoundException {
        //GIVEN
        UserDAO userDAO = mock(UserDAO.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserImpl userImpl = getUser();

        //WHEN
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);
        when(userDAO.findById(anyLong())).thenReturn(Optional.of(userImpl));

        //THEN
        assertNotNull(userServiceImpl.getUserById(1L));
        verify(userDAO).findById(anyLong());
    }

    @Test
    void testGetUserByEmail() {
        //GIVEN
        UserDAO userDAO = mock(UserDAO.class);
        ArrayList<UserImpl> userImplList = new ArrayList<>();
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserImpl user = getUser();
        userImplList.add(user);

        //WHEN
        when(userDAO.findAll()).thenReturn(userImplList);
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);

        //THEN
        assertEquals(userImplList.get(0).getEmail(), userServiceImpl.getUserByEmail("email@example.org").getEmail());
        verify(userDAO).findAll();
    }

    @Test
    void testGetUsersByName4() {
        //GIVEN
        UserImpl user = getUser();
        UserImpl userImpl1 = new UserImpl();
        userImpl1.setEmail("some@example.org");
        userImpl1.setId(1L);
        userImpl1.setName("Name");
        ArrayList<UserImpl> userImplList = new ArrayList<>();
        userImplList.add(userImpl1);
        userImplList.add(user);
        UserDAO userDAO = mock(UserDAO.class);

        //WHEN
        when(userDAO.findAll()).thenReturn(userImplList);
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);

        //THEN
        assertEquals(2, userServiceImpl.getUsersByName("Name", 1, 1).size());
        verify(userDAO).findAll();
    }

    @Test
    void testCreateUser() {
        //GIVEN
        UserDAO userDAO = mock(UserDAO.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        UserImpl user = getUser();
        UserImpl user1 = getUser();

        //WHEN
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);
        when(userDAO.save(any())).thenReturn(user);
        userServiceImpl.createUser(user);

        //THEN
        verify(userDAO).save(any());
        assertSame(user, userServiceImpl.createUser(user1));
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
        verify(userDAO).save(any());
    }

    @Test
    void testDeleteUser() throws EntityNotFoundException {
        //GIVEN
        UserDAO userDAO = mock(UserDAO.class);
        UserServiceImpl userServiceImpl = new UserServiceImpl();
        User user = getUser();

        //WHEN
        ReflectionTestUtils.setField(userServiceImpl, USER_DAO, userDAO);

        //THEN
        assertTrue(userServiceImpl.deleteUser(1L));
        verify(userDAO).deleteById(anyLong());
        verify(userDAO).existsById(anyLong());
    }

    private UserImpl getUser() {
        final UserImpl user = new UserImpl();
        user.setId(1L);
        user.setName("Name");
        user.setEmail("email@example.org");
        return user;
    }
}

