package com.example.demo.account;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {AccountRepositoryTest.Initializer.class})
public class AccountRepositoryTest{

    @Autowired
    private AccountRepository accountRepository;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:1.16.2")
            .withDatabaseName("saifferela")
            .withUsername("postgres")
            .withPassword("root");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    @Transactional
    public void givenUsersInDB_WhenUpdateStatusForNameModifyingQueryAnnotationJPQL_ThenModifyMatchingUsers(){
        insertUsers();
        List<Account> getAll = accountRepository.findAll();
        assertThat(getAll.size()).isEqualTo(4);
    }

    private void insertUsers() {
        accountRepository.save(new Account("SAMPLE", "Sashia", "email@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.save(new Account("SAMPLE1", "Sashia", "email2@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.save(new Account("SAMPLE", "Sashia", "email3@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.save(new Account("SAMPLE3", "Sashia", "email4@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.flush();
    }
}