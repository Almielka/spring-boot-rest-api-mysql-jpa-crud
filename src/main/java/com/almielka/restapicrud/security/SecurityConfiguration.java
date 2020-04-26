package com.almielka.restapicrud.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Security Configuration for two roles:
 * Customer – special role, able to search and order products
 * Content Manager – with general access, able to manage companies and products
 *
 * @author Anna S. Almielka
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String CUSTOMER = "CUSTOMER";
    private static final String MANAGER = "MANAGER";

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("Customer").password(passwordEncoder().encode("Customer")).roles(CUSTOMER)
                .and()
                .withUser("Manager").password(passwordEncoder().encode("Manager")).roles(MANAGER, CUSTOMER);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .httpBasic()
                .and().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/products/**").hasRole(CUSTOMER)
                .antMatchers(HttpMethod.GET, "/api/products/order/**").hasRole(CUSTOMER)
                .antMatchers(HttpMethod.POST, "/api/products/order/**").hasRole(CUSTOMER)
                .antMatchers("/api/products/**").hasRole(MANAGER)
                .antMatchers("/api/companies/**").hasRole(MANAGER)
                .anyRequest().authenticated()
                .and().sessionManagement().disable();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
