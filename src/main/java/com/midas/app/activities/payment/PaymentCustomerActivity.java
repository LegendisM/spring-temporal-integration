package com.midas.app.activities.payment;

import com.midas.app.exceptions.ThirdPartyServiceException;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface PaymentCustomerActivity {
    @ActivityMethod
    String createCustomer(Account account);
}
