package com.example.demo.account;

import com.example.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by adam.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
class UserRepositoryIntegrationTest extends AccountServiceTest {

    @Test
    @Transactional
    public void givenUsersInDBWhenUpdateStatusForNameModifyingQueryAnnotationNativeThenModifyMatchingUsers() {
        accountRepository.save(new Account("SAMPLE", "Sashia", "email@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.save(new Account("SAMPLE1", "Sashia", "email2@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.save(new Account("SAMPLE", "Sashia", "email3@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.save(new Account("SAMPLE3", "Sashia", "email4@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.flush();


        Optional<Account> updatedUsersSize = accountRepository.findAccountByEmail("email3@example.com");

        assertThat(updatedUsersSize.isPresent()).isEqualTo(true);
    }
}