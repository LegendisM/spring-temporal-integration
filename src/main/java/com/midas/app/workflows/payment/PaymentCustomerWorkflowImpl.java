package com.midas.app.workflows.payment;

import com.midas.app.activities.payment.PaymentCustomerActivity;
import com.midas.app.activities.payment.PaypalPaymentCustomerActivityImpl;
import com.midas.app.activities.payment.StripePaymentCustomerActivityImpl;
import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

@WorkflowImpl
public class PaymentCustomerWorkflowImpl implements PaymentCustomerWorkflow {
    private final StripePaymentCustomerActivityImpl stripePaymentCustomerActivity = Workflow.newActivityStub(StripePaymentCustomerActivityImpl.class);
    private final PaypalPaymentCustomerActivityImpl paypalPaymentCustomerActivity = Workflow.newActivityStub(PaypalPaymentCustomerActivityImpl.class);

    @Override
    public String createPaymentCustomer(ProviderType providerType, Account account) {
        PaymentCustomerActivity paymentCustomerActivity = getPaymentCustomerActivity(providerType);
        return paymentCustomerActivity.createCustomer(account);
    }

    private PaymentCustomerActivity getPaymentCustomerActivity(ProviderType providerType) {
        return switch (providerType) {
            case STRIPE -> stripePaymentCustomerActivity;
            case PAYPAL -> paypalPaymentCustomerActivity;
        };
    }
}
