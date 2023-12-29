package com.Sanket.BlogApplication.Repository;

import com.Sanket.BlogApplication.Entities.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepo extends JpaRepository<Authority, Long> {
    @Override
    Optional<Authority> findById(Long id);
}
