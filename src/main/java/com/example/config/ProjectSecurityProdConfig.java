package com.example.config;

import com.example.exception_handling.CustomBasicAuthenticationEntryPoint;
import com.example.filter.CsrfCookieFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
        //http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());

        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();


        http
                .securityContext(contextConfig -> contextConfig.requireExplicitSave(false))
                .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        //config.setAllowedOrigins(List.of("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                }))

                //.sessionManagement(smc ->
                //        smc.invalidSessionUrl("/invalidSession").maximumSessions(1))
                //.csrf(csrf -> csrf.disable())
                .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .ignoringRequestMatchers("/contact", "/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards", "/user").authenticated()
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
