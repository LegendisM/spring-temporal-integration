package com.midas.app.workflows.account;

import com.midas.app.activities.account.AccountActivity;
import com.midas.app.activities.payment.PaymentCustomerActivity;
import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import com.midas.app.services.temporal.TemporalServiceImpl;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WorkflowImpl(taskQueues = CreateAccountWorkflow.QUEUE_NAME)
public class CreateAccountWorkflowImpl implements CreateAccountWorkflow {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final AccountActivity accountActivity =
      Workflow.newActivityStub(AccountActivity.class, TemporalServiceImpl.createActivityOptions());

  private final PaymentCustomerActivity paymentCustomerActivity =
      Workflow.newActivityStub(
          PaymentCustomerActivity.class, TemporalServiceImpl.createActivityOptions());

  @Override
  public Account createAccount(Account details) {
    logger.info("test");
    var account = accountActivity.createAccount(details);
    logger.info("test2");

    var defaultProviderType = ProviderType.STRIPE;
    var paymentCustomerId = paymentCustomerActivity.createCustomer(defaultProviderType, account);

    return accountActivity.updateAccountPaymentInformation(
        account.getId().toString(), defaultProviderType, paymentCustomerId);
  }
}
