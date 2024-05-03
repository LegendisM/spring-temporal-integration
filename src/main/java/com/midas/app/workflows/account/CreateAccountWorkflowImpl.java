package com.midas.app.workflows.account;

import com.midas.app.activities.account.AccountActivity;
import com.midas.app.activities.payment.PaymentCustomerActivity;
import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import com.midas.app.services.temporal.TemporalServiceImpl;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

@WorkflowImpl(taskQueues = CreateAccountWorkflow.QUEUE_NAME)
public class CreateAccountWorkflowImpl implements CreateAccountWorkflow {
    private final AccountActivity accountActivity =
            Workflow.newActivityStub(AccountActivity.class, TemporalServiceImpl.createActivityOptions());

    private final PaymentCustomerActivity paymentCustomerActivity =
            Workflow.newActivityStub(
                    PaymentCustomerActivity.class, TemporalServiceImpl.createActivityOptions());

    @Override
    public Account createAccount(Account details) {
        var account = accountActivity.createAccount(details);

        var defaultProviderType = ProviderType.STRIPE;
        var paymentCustomerId = paymentCustomerActivity.createCustomer(defaultProviderType, account);

        return accountActivity.updateAccountPaymentInformation(
                account.getId().toString(),
                defaultProviderType,
                paymentCustomerId
        );
    }
}
