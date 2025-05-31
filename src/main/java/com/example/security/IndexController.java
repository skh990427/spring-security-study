package com.example.security;

import java.util.concurrent.Callable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndexController {

    private final AsyncService asyncService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/user")
    public String user() {
        return "user";
    }

    @GetMapping("/db")
    public String db() {
        return "db";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/callable") //Callable의 경우 자식 스레드에 시큐리티 컨텍스트가 공유가 됨
    public Callable<Authentication> call() {
        SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
        System.out.println("securityContext = " + securityContext);
        System.out.println("Parent Thread = " + Thread.currentThread().getName());

        return new Callable<Authentication>() {
            @Override
            public Authentication call() throws Exception {
                SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
                System.out.println("securityContext = " + securityContext);
                System.out.println("Parent Thread = " + Thread.currentThread().getName());
                return securityContext.getAuthentication();
            }
        };
    }

    @GetMapping("/async") // @Async 어노테이션 사용시 자식 스레드에 부모 스레드의 내용이 공유가 안됨
    public Authentication async() {
        SecurityContext securityContext = SecurityContextHolder.getContextHolderStrategy().getContext();
        System.out.println("securityContext = " + securityContext);
        System.out.println("Parent Thread = " + Thread.currentThread().getName());

        asyncService.asyncMethod();

        return securityContext.getAuthentication();
    }
}
