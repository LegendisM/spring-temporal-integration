package com.midas.app.activities.account;


import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import com.midas.app.services.account.AccountService;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@ActivityImpl
@Component
@RequiredArgsConstructor
public class AccountActivityImpl implements AccountActivity {
    private final AccountService accountService;

    @Override
    public Account createAccount(Account account) {
        return saveAccount(account);
    }

    @Override
    public Account updateAccountPaymentInformation(String accountId, ProviderType providerType, String providerId) {
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
