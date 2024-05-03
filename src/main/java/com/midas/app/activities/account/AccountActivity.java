package com.midas.app.activities.account;

import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface AccountActivity {
  /**
   * saveAccount saves an account in the data store.
   *
   * @param account is the account to be saved
   * @return Account
   */
  @ActivityMethod
  Account saveAccount(Account account);

  /**
   * createAccount creates a user account in the system
   *
   * @param account is the account to be created
   * @return Account
   */
  @ActivityMethod
  Account createAccount(Account account);

  /**
   * updateAccountPaymentInformation update payment provider information of account
   *
   * @param accountId is the account identifier
   * @param providerType is the type of payment provider
   * @param providerId is the returned customer id by payment provider
   * @return Account
   */
  @ActivityMethod
  Account updateAccountPaymentInformation(String accountId, ProviderType providerType, String providerId);
}
