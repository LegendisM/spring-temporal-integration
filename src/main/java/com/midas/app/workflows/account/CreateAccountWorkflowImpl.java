package com.midas.app.workflows.account;

import com.midas.app.activities.account.AccountActivity;
import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import com.midas.app.workflows.payment.PaymentCustomerWorkflow;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

@WorkflowImpl
public class CreateAccountWorkflowImpl implements CreateAccountWorkflow {
    private final PaymentCustomerWorkflow paymentCustomerWorkflow = Workflow.newChildWorkflowStub(PaymentCustomerWorkflow.class);
    private final AccountActivity accountActivity = Workflow.newActivityStub(AccountActivity.class);

    @Override
    public Account createAccount(Account details) {
        var account = accountActivity.createAccount(details);

        var defaultProviderType = ProviderType.STRIPE;
        var paymentCustomerId = paymentCustomerWorkflow.createPaymentCustomer(defaultProviderType, account);

        return accountActivity.updateAccountPaymentInformation(account.getId().toString(), defaultProviderType, paymentCustomerId);
    }
}
