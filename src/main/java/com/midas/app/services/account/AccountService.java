package com.midas.app.services.account;

import com.midas.app.models.Account;
import java.util.List;
import java.util.Optional;

public interface AccountService {
  /**
   * createAccount creates a new account in the system or provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  Account createAccount(Account details);

  /**
   * getAccounts returns a list of accounts.
   *
   * @return List<Account>
   */
  List<Account> getAccounts();

  /**
   * findById find account by id or throw exception
   *
   * @param id is the account id you want to find by that
   * @return Account
   */
  Account findById(String id);

  /**
   * saveAccount save and return account
   *
   * @param account is the account you want to save that
   * @return Account
   */
  Account saveAccount(Account account);
}
