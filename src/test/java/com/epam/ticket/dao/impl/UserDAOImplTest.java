package com.epam.ticket.dao.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.Ticket;
import com.epam.ticket.model.api.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.epam.ticket.model.impl.TicketImpl;
import com.epam.ticket.model.impl.UserImpl;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

class UserDAOImplTest {

    public static final String STORAGE = "storage";

    @Test
    void testFindAll() {
        //GIVEN
        Storage storage = mock(Storage.class);
        UserDAOImpl userDAOImpl = new UserDAOImpl();
        User user = getUser();

        //WHEN
        when(storage.getUserMap()).thenReturn(Collections.singletonMap(1L, user));
        ReflectionTestUtils.setField(userDAOImpl, STORAGE, storage);

        //THEN
        assertEquals(1L, userDAOImpl.findAll().size());
        verify(storage).getUserMap();
    }

    @Test
    void testFindOne() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        User user = getUser();
        UserDAOImpl userDAOImpl = new UserDAOImpl();

        //WHEN
        ReflectionTestUtils.setField(userDAOImpl, STORAGE, storage);
        when(storage.getUserMap()).thenReturn(Collections.singletonMap(1L, getUser()));

        //THEN
        assertEquals(userDAOImpl.findOne(1L).getId(), user.getId());
        verify(storage).getUserMap();
    }

    @Test
    void testUpdate() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        UserDAOImpl userDAOImpl = new UserDAOImpl();
        User user = getUser();
        User newUser = getUser();
        newUser.setName("newName");
        Map<Long, User> userMap = new HashMap<>();
        userMap.put(1L, user);

        //WHEN
        ReflectionTestUtils.setField(userDAOImpl, STORAGE, storage);
        when(storage.getUserMap()).thenReturn(userMap);
        User updatedUser = userDAOImpl.update(newUser);

        //THEN
        assertNotEquals(updatedUser.getName(), user.getName());
    }

    @Test
    void testCreate() {
        //GIVEN
        Storage storage = mock(Storage.class);
        UserDAOImpl userDAOImpl = new UserDAOImpl();
        User user = getUser();
        User newUser = getUser();
        newUser.setId(2L);
        Map<Long, User> userMap = new HashMap<>();
        userMap.put(1L, user);

        //WHEN
        ReflectionTestUtils.setField(userDAOImpl, STORAGE, storage);
        when(storage.getUserMap()).thenReturn(userMap);

        //THEN
        assertNotEquals(userDAOImpl.create(newUser), user);
        assertEquals(2, userMap.size());
        verify(storage).getUserMap();
    }

    @Test
    void testRemove() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        UserDAOImpl userDAOImpl = new UserDAOImpl();
        User user = getUser();
        Map<Long, User> userMap = new HashMap<>();
        userMap.put(1L, user);

        //WHEN
        ReflectionTestUtils.setField(userDAOImpl, STORAGE, storage);
        when(storage.getUserMap()).thenReturn(userMap);

        //THEN
        assertTrue(userDAOImpl.remove(1L));
    }

    @Test
    void testGetUserByEmail() throws EntityNotFoundException {
        //GIVEN
        Storage storage = mock(Storage.class);
        UserDAOImpl userDAOImpl = new UserDAOImpl();
        User user = getUser();
        HashMap<Long, User> userMap = new HashMap<>();
        userMap.put(1L, user);

        //WHEN
        when(storage.getUserMap()).thenReturn(userMap);
        ReflectionTestUtils.setField(userDAOImpl, STORAGE, storage);

        //THEN
        assertEquals(user, userDAOImpl.getUserByEmail(user.getEmail()));
        verify(storage).getUserMap();
    }

    private User getUser() {
        final User user = new UserImpl();
        user.setId(1L);
        user.setName("Name");
        user.setEmail("email");
        return user;
    }
}

