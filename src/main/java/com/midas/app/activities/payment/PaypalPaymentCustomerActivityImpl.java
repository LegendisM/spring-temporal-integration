package com.midas.app.activities.payment;

import com.midas.app.models.Account;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@ActivityImpl
@Component
@RequiredArgsConstructor
public class PaypalPaymentCustomerActivityImpl implements PaymentCustomerActivity {
    @Override
    public String createCustomer(Account account) {
        throw new UnsupportedOperationException("Paypal invalid customer id operation");
    }
}
