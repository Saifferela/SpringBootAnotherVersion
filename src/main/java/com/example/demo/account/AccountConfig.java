package com.example.demo.account;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class AccountConfig {

    @Bean
    CommandLineRunner commandLineRunner(AccountRepository accountRepository) {
        return args ->
        {
            Account vasyl = new Account("Vasyl", "Danylenko", "saifferela@gmail.com", LocalDate.of(1996, Month.OCTOBER, 24));
            Account kolya = new Account("Kolya", "Krivenkov", "kepgit@gmail.com", LocalDate.of(2000, Month.FEBRUARY, 28));
            accountRepository.saveAll(List.of(vasyl, kolya));
        };
    }
}
