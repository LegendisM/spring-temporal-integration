package com.midas.app.services.account;

import com.midas.app.exceptions.ResourceNotFoundException;
import com.midas.app.models.Account;
import com.midas.app.repositories.AccountRepository;
import com.midas.app.services.temporal.TemporalService;
import com.midas.app.workflows.account.CreateAccountWorkflow;
import com.midas.app.workflows.account.UpdateAccountWorkflow;
import com.midas.generated.model.UpdateAccountDto;
import io.temporal.workflow.Workflow;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
  private final Logger logger = Workflow.getLogger(getClass());

  private final TemporalService temporalService;

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

    var createAccountWorkflow = temporalService.createWorkflowStub(CreateAccountWorkflow.class, CreateAccountWorkflow.QUEUE_NAME, details.getEmail());

    return createAccountWorkflow.createAccount(details);
  }

  /**
   * updateAccount update the information of account.
   *
   * @param updateAccountDto is the new information of the account to be updated.
   * @return Account
   */
  @Override
  public Account updateAccount(String id, UpdateAccountDto updateAccountDto) {
    Account account = findById(id);

    account.setFirstName(updateAccountDto.getFirstName());
    account.setLastName(updateAccountDto.getLastName());
    account.setEmail(updateAccountDto.getEmail());

    var updateAccountWorkflow = temporalService.createWorkflowStub(UpdateAccountWorkflow.class,UpdateAccountWorkflow.QUEUE_NAME, account.getEmail());

    return updateAccountWorkflow.updateAccount(account);
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
    return accountRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("account-not-found"));
  }

  @Override
  public Account saveAccount(Account account) {
    return accountRepository.save(account);
  }
}
