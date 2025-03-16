package com.example.controller;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final CompromisedPasswordChecker compromisedPasswordChecker;

    /*
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {

        try {

            // Check if the password is compromised
            CompromisedPasswordDecision compromisedPasswordDecision =
                    compromisedPasswordChecker.check(customer.getPwd());

            if (compromisedPasswordDecision.isCompromised())
                return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body("The provided password is compromised, please provide a strong password");

            // Encode the password and save the customer
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            Customer savedCustomer = customerRepository.save(customer);

            // Check if the customer was saved successfully
            if (savedCustomer.getId() > 0)
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered.");

            return ResponseEntity
                    .status((HttpStatus.BAD_REQUEST))
                    .body(("User registration failed."));

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occurred: " + e.getMessage());
        }
    }
    */

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(new Date(System.currentTimeMillis()));
            Customer savedCustomer = customerRepository.save(customer);

            if (savedCustomer.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED).
                        body("Given user details are successfully registered");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).
                        body("User registration failed");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).
                    body("An exception occurred: " + ex.getMessage());
        }
    }

    @RequestMapping("/user")
    public Customer getUserDetailsAfterLogin(Authentication authentication) {
        Optional<Customer> optionalCustomer = customerRepository.findByEmail(authentication.getName());
        return optionalCustomer.orElse(null);
    }
}
