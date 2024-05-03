package com.midas.app.workflows.payment;

import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface PaymentCustomerWorkflow {
    String QUEUE_NAME = "payment-customer-workflow";

    @WorkflowMethod
    String createPaymentCustomer(ProviderType providerType, Account account);
}
