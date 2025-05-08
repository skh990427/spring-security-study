package com.example.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class CsrfCookieFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
        if(csrfToken != null) {
            csrfToken.getToken(); // 호출하면 쿠키 만들어지고 렌더링 됨 - 호출 하는 순간 안에 있는 서플라이어가 실행되면서 실제 토큰이 생성되고 쿠키에 저장되는 일이 벌어진다.
        }
        filterChain.doFilter(request, response);
    }
}
