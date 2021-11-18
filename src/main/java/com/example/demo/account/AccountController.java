package com.example.demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAccount(){
        return accountService.getAccount();
    }

    @PostMapping
    public void addAccount(@RequestBody Account account){
        accountService.addAccount(account);
    }

    @DeleteMapping(path = "{accountId}")
    public void deleteAccount(@PathVariable("accountId") Long accountId){
        accountService.deleteAccount(accountId);
    }

    @PutMapping(path = "{accountId}")
    public void updateAccount(
            @PathVariable("accountId") Long accountId,
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) String email)
    {
        accountService.updateAccount(accountId, firstName, lastName, email);
    }

}
