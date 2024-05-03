package com.midas.app.activities.payment;

import com.midas.app.models.Account;
import com.midas.app.providers.external.stripe.StripePaymentProvider;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@ActivityImpl
@Component
@RequiredArgsConstructor
public class StripePaymentCustomerActivityImpl implements PaymentCustomerActivity {
    private final StripePaymentProvider stripePaymentProvider;

    @Override
    public String createCustomer(Account account) {
        return stripePaymentProvider.createCustomer(account);
    }
}
