package com.example.security.method;

import com.example.security.Account;
import java.util.function.Supplier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.authorization.method.MethodInvocationResult;
import org.springframework.security.core.Authentication;

public class MyPostAuthorizationManager implements AuthorizationManager<MethodInvocationResult> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, MethodInvocationResult result) {
        Authentication auth = authentication.get();
        if (auth instanceof AnonymousAuthenticationToken) {
            return new AuthorizationDecision(false);
        }
        Account account = (Account) result.getResult();
        boolean isGranted = account.getOwner().equals(auth.getName());

        return new AuthorizationDecision(isGranted);
    }
}
