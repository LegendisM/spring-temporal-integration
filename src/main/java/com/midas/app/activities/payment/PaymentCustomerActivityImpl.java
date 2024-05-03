package com.midas.app.activities.payment;

import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import com.midas.app.providers.external.stripe.StripePaymentProvider;
import com.midas.app.providers.payment.PaymentProvider;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@ActivityImpl
@Component
@RequiredArgsConstructor
public class PaymentCustomerActivityImpl implements PaymentCustomerActivity {
  private final StripePaymentProvider stripePaymentProvider;

  @Override
  public String createCustomer(ProviderType providerType, Account account) {
    return getPaymentProvider(providerType).createCustomer(account);
  }

  private PaymentProvider getPaymentProvider(ProviderType providerType) {
    return switch (providerType) {
      case STRIPE -> stripePaymentProvider;
      case PAYPAL -> throw new UnsupportedOperationException("Paypal not implemented");
    };
  }
}
