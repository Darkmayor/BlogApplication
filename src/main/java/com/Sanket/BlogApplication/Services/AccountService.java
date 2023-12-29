package com.Sanket.BlogApplication.Services;

import com.Sanket.BlogApplication.Entities.Account;
import com.Sanket.BlogApplication.Entities.Authority;

import com.Sanket.BlogApplication.Repository.AccountRepo;
import com.Sanket.BlogApplication.utilities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements UserDetailsService {
    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Account saveAccount(Account account){
        //encrypting password
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        if(account.getRole() == null){
            account.setRole(Roles.USER.getRole());
        }
        if(account.getPhoto() == null){
            String PhotoPath = "images/person.png";
            account.setPhoto(PhotoPath);
        }
        return accountRepo.save(account);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Account> optionalAccount = accountRepo.findOneByEmailIgnoreCase(email);
        if(!optionalAccount.isPresent()){
            throw new UsernameNotFoundException("Account Not Found");
        }
        Account account = optionalAccount.get();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(account.getRole()));
        //add authority name
        for(Authority _auth: account.getAuthority()){
            grantedAuthorities.add(new SimpleGrantedAuthority(_auth.getName()));
        }
        return new User(account.getEmail() , account.getPassword() , grantedAuthorities);
    }

    //find account by email
    public Optional<Account> findOneByEmail(String email){
        return accountRepo.findOneByEmailIgnoreCase(email);
    }
}
