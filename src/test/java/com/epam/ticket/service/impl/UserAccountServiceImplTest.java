package com.epam.ticket.service.impl;

import com.epam.ticket.dao.UserAccountDAO;
import com.epam.ticket.dao.UserDAO;
import com.epam.ticket.model.impl.UserAccountImpl;
import com.epam.ticket.model.impl.UserImpl;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class UserAccountServiceImplTest {

    public static final String USER_ACCOUNT_DAO = "userAccountDAO";
    public static final String USER_DAO = "userDAO";

    @Test
    void testGetUserAccountById() {
        //GIVEN
        UserAccountServiceImpl userAccountServiceImpl = new UserAccountServiceImpl();
        ReflectionTestUtils.setField(userAccountServiceImpl, USER_DAO, mock(UserDAO.class));
        UserImpl userImpl = getUser();
        UserAccountImpl userAccountImpl = getUserAccount(userImpl);
        UserAccountDAO userAccountDAO = mock(UserAccountDAO.class);

        //WHEN
        when(userAccountDAO.findById(any())).thenReturn(Optional.of(userAccountImpl));
        ReflectionTestUtils.setField(userAccountServiceImpl, USER_ACCOUNT_DAO, userAccountDAO);

        //THEN
        assertSame(userAccountImpl, userAccountServiceImpl.getUserAccountById(1L));
        verify(userAccountDAO).findById(any());
    }

    @Test
    void testCreateUserAccount() {
        //GIVEN
        UserAccountServiceImpl userAccountServiceImpl = new UserAccountServiceImpl();
        UserImpl userImpl = getUser();
        UserAccountImpl userAccountImpl = getUserAccount(userImpl);
        UserAccountDAO userAccountDAO = mock(UserAccountDAO.class);
        UserImpl userImpl1 = getUser();
        UserAccountImpl userAccountImpl1 = getUserAccount(userImpl1);

        //WHEN
        ReflectionTestUtils.setField(userAccountServiceImpl, USER_ACCOUNT_DAO, userAccountDAO);
        ReflectionTestUtils.setField(userAccountServiceImpl, USER_DAO, mock(UserDAO.class));
        when(userAccountDAO.save(any())).thenReturn(userAccountImpl);

        //THEN
        assertSame(userAccountImpl, userAccountServiceImpl.createUserAccount(userAccountImpl1));
        verify(userAccountDAO).save(any());
    }

    @Test
    void testUpdateUserAccount() {
        //GIVEN
        UserAccountServiceImpl userAccountServiceImpl = new UserAccountServiceImpl();
        UserImpl userImpl = getUser();
        UserAccountImpl userAccountImpl = getUserAccount(userImpl);
        UserAccountDAO userAccountDAO = mock(UserAccountDAO.class);
        UserImpl userImpl1 = getUser();
        UserAccountImpl userAccountImpl1 = getUserAccount(userImpl1);

        //WHEN
        when(userAccountDAO.save(any())).thenReturn(userAccountImpl);
        ReflectionTestUtils.setField(userAccountServiceImpl, USER_ACCOUNT_DAO, userAccountDAO);

        //THEN
        assertSame(userAccountImpl, userAccountServiceImpl.updateUserAccount(userAccountImpl1));
        verify(userAccountDAO).save(any());
    }

    @Test
    void testDeleteUserAccount() {
        //GIVEN
        UserDAO userDAO = mock(UserDAO.class);
        UserAccountServiceImpl userAccountServiceImpl = new UserAccountServiceImpl();

        //WHEN
        when(userDAO.existsById(any())).thenReturn(false);
        doNothing().when(userDAO).deleteById(any());
        ReflectionTestUtils.setField(userAccountServiceImpl, USER_DAO, userDAO);
        ReflectionTestUtils.setField(userAccountServiceImpl, USER_ACCOUNT_DAO, mock(UserAccountDAO.class));

        //THEN
        assertTrue(userAccountServiceImpl.deleteUserAccount(1L));
        verify(userDAO).deleteById(any());
        verify(userDAO).existsById(any());
    }

    private UserAccountImpl getUserAccount(UserImpl userImpl) {
        UserAccountImpl userAccountImpl = new UserAccountImpl();
        userAccountImpl.setId(1L);
        userAccountImpl.setPrepaidMoney(1);
        userAccountImpl.setUser(userImpl);
        return userAccountImpl;
    }

    private UserImpl getUser() {
        UserImpl userImpl = new UserImpl();
        userImpl.setEmail("email@example.org");
        userImpl.setId(1L);
        userImpl.setName("Name");
        userImpl.setTickets(new ArrayList<>());
        userImpl.setUserAccounts(new ArrayList<>());
        return userImpl;
    }

}

