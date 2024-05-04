package com.midas.app.workflows.account;

import com.midas.app.activities.account.AccountActivity;
import com.midas.app.activities.payment.PaymentCustomerActivity;
import com.midas.app.models.Account;
import com.midas.app.services.temporal.TemporalServiceImpl;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

@WorkflowImpl(taskQueues = UpdateAccountWorkflow.QUEUE_NAME)
public class UpdateAccountWorkflowImpl implements UpdateAccountWorkflow {
  private final AccountActivity accountActivity =
      Workflow.newActivityStub(AccountActivity.class, TemporalServiceImpl.createActivityOptions());

  private final PaymentCustomerActivity paymentCustomerActivity =
      Workflow.newActivityStub(
          PaymentCustomerActivity.class, TemporalServiceImpl.createActivityOptions());

  @Override
  public Account updateAccount(Account details) {
    var account = accountActivity.updateAccount(details);

    // * Update account changes on customer instance of account in the providers
    paymentCustomerActivity.updateCustomer(account.getProviderType(), account);

    return account;
  }
}
