package com.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user").hasRole("USER")
                        .requestMatchers("/db").hasRole("DB")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
        ;
        return http.build();
    }

//    @Bean
//    public RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_DB\n" + // 디프리케이드 됨 아래 공식문서 권장 방법으로 수정
//                "ROLE_DB > ROLE_USER\n" +
//                "ROLE_USER > ROLE_ANONYMOUS");
//        return roleHierarchy;
//    }

    @Bean
    public RoleHierarchy roleHierarchy() { // .setHierarchy 디프리케이드 됨에 따라 공식문서 권장 방식
        return RoleHierarchyImpl.fromHierarchy("ROLE_ADMIN > ROLE_DB\n" +
                "ROLE_DB > ROLE_USER\n" +
                "ROLE_USER > ROLE_ANONYMOUS");
    }


    /**
     * 스프링 시큐리티 사용자 추가 방법2 - 자바 파일에 빈으로 등록
     * 프로퍼티랑 같이 있으면 얘가 우선순위를 가진다
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}1111").roles("USER").build();
        UserDetails db = User.withUsername("db").password("{noop}1111").roles("DB").build();
        UserDetails admin = User.withUsername("admin").password("{noop}1111").roles("ADMIN", "SECURE").build();
        return new InMemoryUserDetailsManager(user, db, admin);
    }
}
