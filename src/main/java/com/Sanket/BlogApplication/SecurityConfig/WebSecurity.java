package com.Sanket.BlogApplication.SecurityConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true , jsr250Enabled = true)
public class WebSecurity {
    private static final String[] WHITELIST = {
      "/home",
      "/register",
      "/css/**",
      "/images/**",
      "/fonts/**",
      "/js/**",
      "/update_passwords",
        "/login",
        "/update_photo",
        "/forgot-password",
        "/reset_user_password"
    };
    @Bean
    public static PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    } 

    @Bean
    public SecurityFilterChain FilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(WHITELIST).permitAll()
                .requestMatchers("/posts/**").authenticated()
                .requestMatchers("/profile/**").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/editor/**").hasAnyRole("ADMIN" , "EDITOR")
                .and()
                .formLogin()
                .loginPage("/login").loginProcessingUrl("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/home" , true)
                .failureUrl("/login/error")
                .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/home")
                .and()
                .rememberMe().rememberMeParameter("remember-me")
                .and()
                .httpBasic();
        // creating a form using login page and if anyone tries to access any page without
        // login it will be redirected to login page.
       httpSecurity.csrf().disable();
       httpSecurity.headers().frameOptions().disable();


            return httpSecurity.build();
    }
}
