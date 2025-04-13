//package com.example.security.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//@EnableWebSecurity
//@Configuration
//public class SecurityConfig2 {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManagerBuilder builder, AuthenticationConfiguration configuration) throws Exception {
//
//        AuthenticationManagerBuilder managerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        managerBuilder.authenticationProvider(authenticationProvider());
//
//        ProviderManager authenticationManager = (ProviderManager) configuration.getAuthenticationManager();
//        authenticationManager.getProviders().remove(0); // 빈으로 등록된건 삭제 처리하고
//        builder.authenticationProvider(new DaoAuthenticationProvider()); //DaoAuthenticationProvider 추가
//
//        http
//                .authorizeHttpRequests(auth -> auth
////                        .requestMatchers("/").permitAll()
//                        .anyRequest().authenticated())
//                .formLogin(Customizer.withDefaults())
////                .authenticationProvider(new CustomAuthenticationProvider()) // 위에 3줄이랑 동일하게 동작함
////                .authenticationProvider(new CustomAuthenticationProvider2())
//        ;
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        return new CustomAuthenticationProvider();
//    }
//
//    /**
//     * 스프링 시큐리티 사용자 추가 방법2 - 자바 파일에 빈으로 등록
//     * 프로퍼티랑 같이 있으면 얘가 우선순위를 가진다
//     */
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withUsername("user")
//                .password("{noop}1111")
//                .authorities("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//}
