package com.example.demo.account;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(initializers = {AccountRepositoryTest.Initializer.class})
class AccountRepositoryTest extends AccountServiceTest {


    @ClassRule
    protected static final PostgreSQLContainer postgreSQLContainer;

    static {
        postgreSQLContainer = new PostgreSQLContainer("postgres:9.6.15")
                .withDatabaseName("socialmediasite")
                .withUsername("postgres")
                .withPassword("password");
        //Mapped port can only be obtained after container is started.
        postgreSQLContainer.start();
    }

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
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
    public void givenUsersInDB_WhenUpdateStatusForNameModifyingQueryAnnotationNative_ThenModifyMatchingUsers(){
        insertUsers();
        Optional<Account> updatedUsersSize = accountRepository.findAccountByEmail("email2@example.com");
        assertThat(updatedUsersSize).isEqualTo(true);
    }

    private void insertUsers() {
        accountRepository.save(new Account("SAMPLE", "Sashia", "email@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.save(new Account("SAMPLE1", "Sashia", "email2@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.save(new Account("SAMPLE", "Sashia", "email3@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.save(new Account("SAMPLE3", "Sashia", "email4@example.com", LocalDate.of(2005, Month.APRIL, 21)));
        accountRepository.flush();
    }
}