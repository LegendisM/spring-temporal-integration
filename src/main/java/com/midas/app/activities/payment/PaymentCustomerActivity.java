package com.midas.app.activities.payment;

import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PaymentCustomerActivity {
  @ActivityMethod
  String createCustomer(ProviderType providerType, Account account);

  @ActivityMethod
  void updateCustomer(ProviderType providerType, Account account);
}
