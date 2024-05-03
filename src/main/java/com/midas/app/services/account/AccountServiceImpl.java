package com.midas.app.services.account;

import com.midas.app.exceptions.ResourceNotFoundException;
import com.midas.app.models.Account;
import com.midas.app.repositories.AccountRepository;
import com.midas.app.services.temporal.WorkflowService;
import com.midas.app.workflows.account.CreateAccountWorkflow;
import io.temporal.workflow.Workflow;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final Logger logger = Workflow.getLogger(AccountServiceImpl.class);

  private final WorkflowService workflowService;

  private final AccountRepository accountRepository;

  /**
   * createAccount creates a new account in the system or provider.
   *
   * @param details is the details of the account to be created.
   * @return Account
   */
  @Override
  public Account createAccount(Account details) {
    logger.info("initiating workflow to create account for email: {}", details.getEmail());

    var accountWorkflow = workflowService.createWorkflowStub(
            CreateAccountWorkflow.class,
            CreateAccountWorkflow.QUEUE_NAME,
            details.getEmail()
    );

    return accountWorkflow.createAccount(details);
  }

  /**
   * getAccounts returns a list of accounts.
   *
   * @return List<Account>
   */
  @Override
  public List<Account> getAccounts() {
    return accountRepository.findAll();
  }

  @Override
  public Account findById(String id) {
    return accountRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("account-not-found"));
  }

  @Override
  public Account saveAccount(Account account) {
    return accountRepository.save(account);
  }
}
