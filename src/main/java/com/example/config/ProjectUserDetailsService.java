package com.example.config;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // for @Autowired CustomerRepository
public class ProjectUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;
    /**
     * @param username the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Customer customer = customerRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(customer.getRole()));

        return new User(customer.getEmail(), customer.getPwd(), authorities);
    }
}
