package com.example.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAccount() {
        return accountRepository.findAll();
    }

    public void addAccount(Account account) {
        if (accountRepository.findAccountByEmail(account.getEmail()).isPresent())
            throw new IllegalStateException("This email already exists!");
        accountRepository.save(account);
    }

    public void deleteAccount(Long accountId) {
        if (!accountRepository.existsById(accountId))
            throw new IllegalStateException("Account with id" + accountId + " does not exists!");
        accountRepository.deleteById(accountId);

    }

    @Transactional
    public void updateAccount(Long accountId, String firstName, String lastName, String email) {
        Account account = accountRepository.findById(accountId).orElseThrow(
                () -> new IllegalStateException("Student with id " + accountId + " does not exists!")
        );

        if (firstName != null && firstName.length() > 0 && !Objects.equals(account.getFirstName(), firstName))
            account.setFirstName(firstName);

        if (lastName != null && lastName.length() > 0 && !Objects.equals(account.getLastName(), lastName))
            account.setLastName(lastName);

        if (email != null && email.length() > 0 && !Objects.equals(account.getEmail(), email)) {
            Optional<Account> accountOptional = accountRepository.findAccountByEmail(email);
            if (accountOptional.isPresent()) {
                throw new IllegalStateException("email taken");
            }
            account.setEmail(email);
        }
    }
}
