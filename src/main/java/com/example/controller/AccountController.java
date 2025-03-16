package com.example.controller;

import com.example.model.Accounts;
import com.example.repository.AccountsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountsRepository accountsRepository;

    /*

    @GetMapping("/myAccount")
    public String getAccountDetails() {

        return "Here is the account from the DB";
    }
    */

    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam long id) {
        Accounts accounts = accountsRepository.findByCustomerId(id);

        if (accounts != null) {
            return accounts;
        }

        return null;

    }


}
