package com.Sanket.BlogApplication.Config;

import com.Sanket.BlogApplication.Entities.Account;
import com.Sanket.BlogApplication.Entities.Authority;
import com.Sanket.BlogApplication.Entities.Post;
import com.Sanket.BlogApplication.Services.AccountService;
import com.Sanket.BlogApplication.Services.AuthorityService;
import com.Sanket.BlogApplication.Services.PostService;
import com.Sanket.BlogApplication.utilities.Privileges;
import com.Sanket.BlogApplication.utilities.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class SeedData implements CommandLineRunner {

    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuthorityService authorityService;
    @Override
    public void run(String... args) throws Exception {

        //iterate over authrity to set value for each users authority
        for(Privileges auth: Privileges.values()){
            //create authorities
            Authority authority = new Authority();
            authority.setId(auth.getPrivilageId());
            authority.setName(auth.getPrivilageName());
            //saving authorities
            authorityService.save(authority);
        }
        Account account01 = new Account();
        account01.setEmail("demoAccount01@gmail.com");
        account01.setPassword("demo01@001");
        account01.setFirstName("Sanket");
        account01.setLastName("Kalokhe");
        account01.setRole(Roles.ADMIN.getRole());
        account01.setAge(20);
        account01.setDate_Of_Birth(LocalDate.parse("2002-10-07"));
        account01.setGender("Male");
        accountService.saveAccount(account01);

        Account account02 = new Account();
        account02.setEmail("demoAccount02@gmail.com");
        account02.setPassword("demo01@002");
        account02.setFirstName("Priyanka");
        account02.setLastName("Kalokhe");
        account02.setRole(Roles.EDITOR.getRole());
        account02.setAge(22);
        account02.setDate_Of_Birth(LocalDate.parse("2000-02-17"));
        account02.setGender("Female");

        Set<Authority> authority = new HashSet<>();
        authorityService.findById(Privileges.ACCESS_ADMIN_PANEL.getPrivilageId()).ifPresent(authority::add);
        authorityService.findById(Privileges.RESET_ANY_USER_PASSWORD.getPrivilageId()).ifPresent((authority::add));
        account02.setAuthority(authority);
        accountService.saveAccount(account02);

        Account account03 = new Account();
        account03.setEmail("demoAccount03@gmail.com");
        account03.setPassword("demo01@003");
        account03.setFirstName("Dhanesh");
        account03.setLastName("Dhatrak");
        account03.setAge(20);
        account03.setDate_Of_Birth(LocalDate.parse("2002-10-22"));
        account03.setGender("Male");
        accountService.saveAccount(account03);

        Account account04 = new Account();
        account04.setEmail("demoAccount04@gmail.com");
        account04.setPassword("demo01@004");
        account04.setFirstName("Alish");
        account04.setLastName("Shaikh");
        account04.setAge(20);
        account04.setDate_Of_Birth(LocalDate.parse("2002-12-27"));
        account04.setGender("Male");
        accountService.saveAccount(account04);

        List<Post> posts = new ArrayList<>();
        if(posts.size() == 0){
            Post post01 = new Post();
            post01.setTitle("Post01");
            post01.setBody("Inside post01");
            post01.setAccount(account01);
            postService.savePost(post01);

            Post post02 = new Post();
            post02.setTitle("POst02");
            post02.setBody("Inside post02");
            post02.setAccount(account02);
            postService.savePost(post02);
        }

    }
}
