package com.example.demo.account;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    Account account = new Account(1L,
            "Vanya",
            "Svitenko",
            "sitvan@gmail.com",
            LocalDate.of(2005, Month.APRIL, 12)
    );

    @Mock
    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        accountService = new AccountService(accountRepository);
    }

    @Test
    void getAccount() {

        accountService.getAccount();

        verify(accountRepository).findAll();
    }

    @Test
    void addAccount() {

        accountService.addAccount(account);

        ArgumentCaptor<Account> studentArgumentCaptor =
                ArgumentCaptor.forClass(Account.class);

        verify(accountRepository)
                .save(studentArgumentCaptor.capture());

        Account capturedStudent = studentArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(account);
    }

    @Test
    void deleteAccount() {
        long id = 10;

        given(accountRepository.existsById(id)).willReturn(true);

        accountService.deleteAccount(id);

        verify(accountRepository).deleteById(id);
    }

    @Test
    void updateAccount() {

        Account van = new Account(1L,
                "Van",
                "Svite",
                "sitn@gmail.com",
                LocalDate.of(2005, Month.APRIL, 12)
        );

        given(accountRepository.findById(1L)).willReturn(Optional.ofNullable(account));

        accountService.updateAccount(van.getId(), van.getFirstName(), van.getLastName(), van.getEmail());
    }
}