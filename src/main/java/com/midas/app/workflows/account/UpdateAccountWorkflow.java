package com.midas.app.workflows.account;

import com.midas.app.models.Account;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface UpdateAccountWorkflow {
  String QUEUE_NAME = "update-account-workflow";

  /**
   * updateAccount update a new account information in the system and provider.
   *
   * @param details is the details of the account to be updated.
   * @return Account
   */
  @WorkflowMethod
  Account updateAccount(Account details);
}
