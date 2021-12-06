package com.epam.ticket.service.api;

import com.epam.ticket.model.api.UserAccount;

public interface UserAccountService {
    /**
     * Gets userAccount by user's id.
     *
     * @return UserAccount.
     */
    UserAccount getUserAccountById(long userId);

    /**
     * Creates new Account for user. id should be auto-generated.
     *
     * @param userAccount UserAccount data.
     * @return Created UserAccount object.
     */
    UserAccount createUserAccount(UserAccount userAccount);

    /**
     * Updates user's account using given data.
     *
     * @param userAccount User data for update. Should have id set.
     * @return Updated User object.
     */
    UserAccount updateUserAccount(UserAccount userAccount);

    /**
     * Deletes user's account by its id.
     *
     * @param userAccountId User account id.
     * @return Flag that shows whether user's account has been deleted.
     */
    boolean deleteUserAccount(long userAccountId);

    /**
     * Refills prepaid money for all accounts.
     */
    void refillPrepaidMoneyForAll();
}
