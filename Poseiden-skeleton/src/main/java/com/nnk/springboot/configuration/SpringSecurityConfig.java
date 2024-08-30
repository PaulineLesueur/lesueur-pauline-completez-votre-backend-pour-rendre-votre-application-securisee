package com.nnk.springboot.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    /**
     * Configures the security filter chain, defining which endpoints are accessible by
     * which roles and configuring the login and logout behavior.
     *
     * @param http the HttpSecurity object that allows configuring web-based security for specific HTTP requests.
     * @return a configured SecurityFilterChain instance.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    // Allow public access to static resources (CSS)
                    auth.requestMatchers("/css/**").permitAll();
                    // Allow public access to the login page and home page
                    auth.requestMatchers("/login", "/").permitAll();
                    // Restrict access to /user/** endpoints to users with ADMIN role
                    auth.requestMatchers("/user/**").hasRole("ADMIN");
                    // All other requests require authentication
                    auth.anyRequest().authenticated();
                })
                .formLogin(form ->
                        form.loginPage("/login")
                                .permitAll()
                                .defaultSuccessUrl("/bidList/list", true))
                .logout(logoutConfigurer -> {
                    // Configure the logout behavior
                    logoutConfigurer
                            .logoutUrl("/app-logout")
                            .logoutSuccessUrl("/login")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID");
                })
                .build();
    }

    /**
     * Provides a BCryptPasswordEncoder bean to be used for encoding passwords.
     *
     * @return a BCryptPasswordEncoder instance.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication manager with a custom user details service and
     * password encoder. This sets up the user authentication process.
     *
     * @param http the HttpSecurity object used to obtain the AuthenticationManagerBuilder.
     * @param bCryptPasswordEncoder the password encoder to be used for encoding user passwords.
     * @return a configured AuthenticationManager instance.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }

}
