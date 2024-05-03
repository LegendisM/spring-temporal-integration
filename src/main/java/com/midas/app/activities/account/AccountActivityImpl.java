package com.midas.app.activities.account;

import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import com.midas.app.services.account.AccountService;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ActivityImpl
@Component
@RequiredArgsConstructor
public class AccountActivityImpl implements AccountActivity {
  //  private final Logger logger = Workflow.getLogger(getClass());
  private final Logger logger = LoggerFactory.getLogger(getClass());
  private final AccountService accountService;

  @Override
  public Account createAccount(Account account) {
    logger.info("todo createAccount called");
    var result = saveAccount(account);
    logger.info("New account created " + result.getId());
    return result;
  }

  @Override
  public Account updateAccountPaymentInformation(
      String accountId, ProviderType providerType, String providerId) {
    var account = accountService.findById(accountId);

    account.setProviderType(providerType);
    account.setProviderId(providerId);

    return saveAccount(account);
  }

  @Override
  public Account saveAccount(Account account) {
    return accountService.saveAccount(account);
  }
}
