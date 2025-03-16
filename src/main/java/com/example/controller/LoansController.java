package com.example.controller;

import com.example.model.Loans;
import com.example.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LoansController {

    private final LoanRepository loanRepository;

    //@GetMapping("/myLoans")
    //public String getLoansDetails() {
    //    return "Here are the loans from the DB";
    //}

    @GetMapping("/myLoans")
    public List<Loans> getLoanDetails(@RequestParam long id) {

        List<Loans> loans = loanRepository.findByCustomerIdOrderByStartDtDesc(id);

        if (loans != null) {
            return loans;
        }

        return null;
    }
}
