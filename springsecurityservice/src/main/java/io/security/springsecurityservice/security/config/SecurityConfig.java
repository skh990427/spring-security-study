package io.security.springsecurityservice.security.config;

import io.security.springsecurityservice.security.filter.RestAuthenticationFilter;
import io.security.springsecurityservice.security.handler.FormAccessDeniedHandler;
import io.security.springsecurityservice.security.handler.FormAuthenticationFailureHandler;
import io.security.springsecurityservice.security.handler.FormAuthenticationSuccessHandler;
import io.security.springsecurityservice.security.handler.RestAuthenticationFailureHandler;
import io.security.springsecurityservice.security.handler.RestAuthenticationSuccessHandler;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationProvider restAuthenticationProvider;
    private final AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> authenticationDetailsSource;
    private final FormAuthenticationSuccessHandler successHandler;
    private final FormAuthenticationFailureHandler failureHandler;
    private final RestAuthenticationSuccessHandler restSuccessHandler;
    private final RestAuthenticationFailureHandler restFailureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                .requestMatchers("/", "/signup", "/login*").permitAll()
                .requestMatchers("/user").hasAuthority("ROLE_USER")
                .requestMatchers("/manager").hasAuthority("ROLE_MANAGER")
                .requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated())
            .formLogin(
                form -> form
                    .loginPage("/login")
                    .authenticationDetailsSource(authenticationDetailsSource)
                    .successHandler(successHandler)
                    .failureHandler(failureHandler)
                    .permitAll())
            .authenticationProvider(authenticationProvider)
            .exceptionHandling(exception -> exception
                .accessDeniedHandler(new FormAccessDeniedHandler("/denied")))
        ;
        return http.build();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain restSecurityFilterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
            http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(restAuthenticationProvider);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
            .securityMatcher("/api/**")
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.*", "/*/icon-*").permitAll()
                .anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .addFilterBefore(restAuthenticationFilter(http, authenticationManager),
                             UsernamePasswordAuthenticationFilter.class)
            .authenticationManager(authenticationManager)
        ;
        return http.build();
    }

    private RestAuthenticationFilter restAuthenticationFilter(HttpSecurity http,
                                                              AuthenticationManager authenticationManager) {
        RestAuthenticationFilter restAuthenticationFilter = new RestAuthenticationFilter(http);
        restAuthenticationFilter.setAuthenticationManager(authenticationManager);
        restAuthenticationFilter.setAuthenticationSuccessHandler(restSuccessHandler);
        restAuthenticationFilter.setAuthenticationFailureHandler(restFailureHandler);
        return restAuthenticationFilter;
    }
}
