package com.midas.app.workflows.account;

import com.midas.app.activities.account.AccountActivity;
import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import com.midas.app.workflows.payment.PaymentCustomerWorkflow;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WorkflowImpl
public class CreateAccountWorkflowImpl implements CreateAccountWorkflow {
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final PaymentCustomerWorkflow paymentCustomerWorkflow =
      Workflow.newChildWorkflowStub(PaymentCustomerWorkflow.class);
  private final AccountActivity accountActivity = Workflow.newActivityStub(AccountActivity.class);

  @Override
  public Account createAccount(Account details) {
    logger.info("test");
    var account = accountActivity.createAccount(details);
    logger.info("test2");

    var defaultProviderType = ProviderType.STRIPE;
    var paymentCustomerId =
        paymentCustomerWorkflow.createPaymentCustomer(defaultProviderType, account);

    return accountActivity.updateAccountPaymentInformation(
        account.getId().toString(), defaultProviderType, paymentCustomerId);
  }
}
