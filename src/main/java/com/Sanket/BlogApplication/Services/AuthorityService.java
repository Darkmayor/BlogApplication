package com.Sanket.BlogApplication.Services;

import com.Sanket.BlogApplication.Repository.AuthorityRepo;
import com.Sanket.BlogApplication.Entities.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityService {

    @Autowired
    private AuthorityRepo authorityRepo;
    public Authority save(Authority authority){

        return authorityRepo.save(authority);
    }

    public Optional<Authority> findById(Long id){

        return authorityRepo.findById(id);
    }
}
