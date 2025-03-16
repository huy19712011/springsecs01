package com.example.controller;

import com.example.model.AccountTransactions;
import com.example.repository.AccountTransactionsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BalanceController {

    private final AccountTransactionsRepository accountTransactionsRepository;

    //@GetMapping("/myBalance")
    //public String getBalanceDetails() {
    //
    //    return "Here is the balance from the DB";
    //}

    @GetMapping("/myBalance")
    public List<AccountTransactions> getBalanceDetails(@RequestParam long id) {

        List<AccountTransactions> accountTransactions
                = accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(id);

        if (accountTransactions != null) {
            return accountTransactions;
        }

        return null;
    }
}
