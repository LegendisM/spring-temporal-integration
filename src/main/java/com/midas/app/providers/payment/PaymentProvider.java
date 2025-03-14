package com.midas.app.providers.payment;

import com.midas.app.models.Account;

public interface PaymentProvider {
  /** providerName is the name of the payment provider */
  String providerName();

  /**
   * createCustomer creates a new customer in the payment provider.
   *
   * @param account is the account instance
   * @return id is the returned customer id of payment provider
   */
  String createCustomer(Account account);

  /**
   * updateCustomer update existed customer in the payment provider.
   *
   * @param account is the account instance for retrieve updated data
   */
  void updateCustomer(Account account);
}
