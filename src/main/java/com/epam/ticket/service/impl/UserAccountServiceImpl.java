package com.epam.ticket.service.impl;

import com.epam.ticket.dao.UserAccountDAO;
import com.epam.ticket.dao.UserDAO;
import com.epam.ticket.exception.EntityNotFoundException;
import com.epam.ticket.model.api.UserAccount;
import com.epam.ticket.model.impl.UserAccountImpl;
import com.epam.ticket.service.api.UserAccountService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class);
    public static final int PREPAID_MONEY = 10000;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserAccountDAO userAccountDAO;

    @Override
    public UserAccount getUserAccountById(long userAccountId) {
        try {
            return this.userAccountDAO.findById(userAccountId).orElseThrow(EntityNotFoundException::new);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot find user's account with ID [%s]", userAccountId), e);
        }
        return new UserAccountImpl();
    }

    @Override
    public UserAccount createUserAccount(UserAccount userAccount) {
        return this.userAccountDAO.save((UserAccountImpl) userAccount);
    }

    @Override
    public UserAccount updateUserAccount(UserAccount userAccount) {
        return this.userAccountDAO.save((UserAccountImpl) userAccount);
    }

    @Override
    public boolean deleteUserAccount(long userAccountId) {
        try {
            this.userDAO.deleteById(userAccountId);
            return !this.userDAO.existsById(userAccountId);
        } catch (EntityNotFoundException e) {
            LOGGER.error(String.format("Cannot delete user's account with ID [%s]", userAccountId), e);
        }
        return Boolean.FALSE;
    }

    public void refillPrepaidMoneyForAll() {
        userAccountDAO.findAll().forEach(userAccount -> {
            userAccount.setPrepaidMoney(PREPAID_MONEY);
            this.userAccountDAO.save(userAccount);
        });
    }
}
