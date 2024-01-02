package com.Sanket.BlogApplication.Repository;

import com.Sanket.BlogApplication.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account , Long> {


    Optional<Account> findOneByEmailIgnoreCase(String email);

    Optional<Account> findBytoken(String password_reset_token);



}
