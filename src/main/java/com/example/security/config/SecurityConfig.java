package com.example.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/loginPage")
                        .loginProcessingUrl("/loginProc")
                        .defaultSuccessUrl("/", true) //핸들러 옵션이 있으면 핸들러가 우선
                        .failureUrl("/failed") //핸들러 옵션이 있으면 핸들러가 우선
                        .usernameParameter("userId")
                        .passwordParameter("pwd")
                        .successHandler((request, response, authentication) -> {
                            System.out.println("authentication : " + authentication);
                            response.sendRedirect("/home");
                        })
                        .failureHandler((request, response, exception) -> {
                            System.out.println("exception : " + exception.getMessage());
                            response.sendRedirect("/login");
                        })
                        .permitAll()
                );

        return http.build();
    }

    /**
     * 스프링 시큐리티 사용자 추가 방법2 - 자바 파일에 빈으로 등록
     * 프로퍼티랑 같이 있으면 얘가 우선순위를 가진다
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user")
                .password("{noop}1111")
                .authorities("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
