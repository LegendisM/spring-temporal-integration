package com.midas.app.workflows.payment;

import com.midas.app.activities.payment.PaymentCustomerActivity;
import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import com.midas.app.services.temporal.TemporalServiceImpl;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

@WorkflowImpl(taskQueues = PaymentCustomerWorkflow.QUEUE_NAME)
public class PaymentCustomerWorkflowImpl implements PaymentCustomerWorkflow {
  private final PaymentCustomerActivity paymentCustomerActivity =
      Workflow.newActivityStub(
          PaymentCustomerActivity.class, TemporalServiceImpl.createActivityOptions());

  @Override
  public String createPaymentCustomer(ProviderType providerType, Account account) {
    return paymentCustomerActivity.createCustomer(providerType, account);
  }
}
