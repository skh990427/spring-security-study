package io.security.springsecurityservice.users.repository;

import io.security.springsecurityservice.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}
