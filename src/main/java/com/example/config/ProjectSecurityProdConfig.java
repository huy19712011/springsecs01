package com.example.config;

import com.example.exception_handling.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
        http
                .sessionManagement(smc -> smc.invalidSessionUrl("/invalidSession"))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(requests -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/contact", "/notices", "/error", "/register", "/invalidSession").permitAll()
        );
        //http.formLogin(flc -> flc.disable()); // flc = form login configuration
        http.formLogin(withDefaults());
        http.httpBasic(hbc ->
                hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        return http.build();
    }

    /*
    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {

        return new JdbcUserDetailsManager(dataSource);


        //UserDetails user = User
        //        .withUsername("user")
        //        //.password("{noop}123456")
        //        .password("{noop}userblah@123456")
        //        .authorities("read")
        //        .build();

        //UserDetails admin = User
        //        .withUsername("admin")
        //        //.password("{bcrypt}$2a$12$8.7CBbjoxldfb/ovHaDg1eCjt6VrtbmI9gFpkM9FCetkfxWFTFpa.") //123456, bcrypt = default
        //        .password("{bcrypt}$2a$12$ZPWkZXZcRY964BojdVimbeoFEDsWlDea1TR5B1RLNFZ4x3Lo/Crde") //adminblah@123456, bcrypt = default
        //        .authorities("admin")
        //        .build();

        //return new InMemoryUserDetailsManager(user, admin);

        //List<UserDetails> userDetailsList = List.of(user, admin);
        //return new InMemoryUserDetailsManager(userDetailsList);

    }
    */


    //https://bcrypt-generator.com/
    @Bean
    public PasswordEncoder passwordEncoder() {

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {

        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
