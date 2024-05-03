package com.midas.app.workflows.payment;

import com.midas.app.activities.payment.PaymentCustomerActivityImpl;
import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import com.midas.app.services.temporal.TemporalServiceImpl;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

@WorkflowImpl
public class PaymentCustomerWorkflowImpl implements PaymentCustomerWorkflow {
  private final PaymentCustomerActivityImpl paymentCustomerActivity =
      Workflow.newActivityStub(
          PaymentCustomerActivityImpl.class, TemporalServiceImpl.createActivityOptions());

  @Override
  public String createPaymentCustomer(ProviderType providerType, Account account) {
    return paymentCustomerActivity.createCustomer(providerType, account);
  }
}
