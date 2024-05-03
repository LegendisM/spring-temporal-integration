package com.midas.app.providers.external.stripe;

import com.midas.app.exceptions.ThirdPartyServiceException;
import com.midas.app.models.Account;
import com.midas.app.providers.payment.PaymentProvider;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Getter
public class StripePaymentProvider implements PaymentProvider {
    private final Logger logger = LoggerFactory.getLogger(StripePaymentProvider.class);

    private final StripeConfiguration configuration;

    public StripePaymentProvider(StripeConfiguration configuration) {
        this.configuration = configuration;
        Stripe.apiKey = configuration.getApiKey();
    }

    /**
     * providerName is the name of the payment provider
     */
    @Override
    public String providerName() {
        return "stripe";
    }

    /**
     * createCustomer creates a new customer in the payment provider.
     *
     * @param account is the account instance
     * @return id is the returned customer id of payment provider
     */
    @Override
    public String createCustomer(Account account) {
        CustomerCreateParams params =
                CustomerCreateParams.builder()
                        .setEmail(account.getEmail())
                        .setName(account.getFirstName() + " " + account.getLastName())
                        .build();
        try {
            var customer = Customer.create(params);
            logger.info("Stripe customer created with id " + customer.getId());
            return customer.getId();
        } catch (StripeException exception) {
            throw new ThirdPartyServiceException(exception.getUserMessage());
        }
    }

    @Override
    public void updateCustomer(Account account) {
        CustomerUpdateParams params = CustomerUpdateParams.builder()
                .setName(account.getFirstName() + " " + account.getLastName())
                .setEmail(account.getEmail())
                .build();
        try {
            var customer = Customer.retrieve(account.getProviderId());
            customer.update(params);
            logger.info("Stripe customer updated with id " + customer.getId());
        } catch (StripeException exception) {
            throw new ThirdPartyServiceException(exception.getUserMessage());
        }
    }
}
