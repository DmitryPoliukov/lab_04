package com.epam.esm.security;

import com.epam.esm.filter.CustomAuthenticationFilter;
import com.epam.esm.filter.CustomAuthorizationFilter;
import com.epam.esm.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(CustomAuthorizationFilter customAuthorizationFilter, UserDetailsServiceImpl userDetailsService) {
        this.customAuthorizationFilter = customAuthorizationFilter;
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers(GET, "/certificates/**").permitAll()
                .antMatchers(POST, "/users/register", "/users/auth", "/login").permitAll();

        http.authorizeRequests().antMatchers(POST, "/orders").fullyAuthenticated()
                .antMatchers(GET, "/tags/**", "/users/**", "/orders/**").fullyAuthenticated();
        http.authorizeRequests().anyRequest().hasRole(ADMIN);
        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }


}
