package com.epam.esm.security;

import com.epam.esm.filter.CustomAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String ADMIN = "ADMIN";
    private final CustomAuthorizationFilter customAuthorizationFilter;

    public SecurityConfig(CustomAuthorizationFilter customAuthorizationFilter) {
        this.customAuthorizationFilter = customAuthorizationFilter;
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers(GET, "/certificates/**", "/users").permitAll()
                .antMatchers(POST, "/users/register", "/users/auth").permitAll();

        http.authorizeRequests().antMatchers(POST, "/orders").fullyAuthenticated()
                .antMatchers(GET, "/tags/**", "/users/**").fullyAuthenticated();
        http.authorizeRequests().anyRequest().hasRole(ADMIN);
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
