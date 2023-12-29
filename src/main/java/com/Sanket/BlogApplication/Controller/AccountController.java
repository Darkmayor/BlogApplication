package com.Sanket.BlogApplication.Controller;

import com.Sanket.BlogApplication.Entities.Account;

import com.Sanket.BlogApplication.Services.AccountService;
import com.Sanket.BlogApplication.utilities.AppUtils;

import jakarta.validation.Valid;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {
    @Autowired
    private AccountService accountService;

   

    @GetMapping("/register")
    public String Register(Model model){
        Account account = new Account();
        model.addAttribute("account" , account);
        return "account_view/register";
    }


    @PostMapping("/register")
    public String Register_User(@Valid @ModelAttribute Account account , BindingResult result){
        if(result.hasErrors()){
            return "account_view/register";
        }
        accountService.saveAccount(account);
        //we can use redirect attribute to make redirect to any specific page
        return "account_view/login";
    }

    @GetMapping("/login")
    public String Login(Model model){
        return "account_view/login";
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String profile(Model model , Principal principal){
        String authUser = SecurityContextHolder.getContext().getAuthentication().getName();
        // String authUser = "email";
        
        // if(principal != null){
        //     authUser = principal.getName();
        // }
            Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
            if(optionalAccount.isPresent() ){
                Account account = optionalAccount.get();
                model.addAttribute("account", account); 
                model.addAttribute("photo", account.getPhoto());
                return "account_view/profile";
            }

        else{
            return "redirect:/?error";
        }
    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public String UpdateProfile(@Valid @ModelAttribute Account account , BindingResult result){
        
        if(result.hasErrors()){
            return "account_view/profile";
        }
        String authUser = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if(optionalAccount.isPresent()){
            //if account is present then modify
            Account UpdatedAccount = optionalAccount.get();
            UpdatedAccount.setAge(account.getAge());
            UpdatedAccount.setDate_Of_Birth(account.getDate_Of_Birth());
            UpdatedAccount.setEmail(account.getEmail());
            UpdatedAccount.setFirstName(account.getFirstName());
            UpdatedAccount.setLastName(account.getLastName());
            accountService.saveAccount(UpdatedAccount);
            return "redirect:/home";
        }
        return "404";
    }

    @PostMapping("/update_photo")
    @PreAuthorize("isAuthenticated()")
    public String uploadPhoto(@RequestParam("file") MultipartFile file, RedirectAttributes Attributes){
        if(file.isEmpty()){
            Attributes.addFlashAttribute("error" , "No File Uploaded");
            return "redirect:/profile";
        }
        String FileName = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            //generate a random String using len , numbers , letters
            int length = 10;
            boolean useletters = true;
            boolean useNumbers = true;
            String generatedString = RandomStringUtils.random(length,useletters,useNumbers);
            //create a name for file
            String final_photo_name = generatedString + FileName;
            //get absolutefile location
            String AbsoluteFIleLocation = AppUtils.getUploadPath(final_photo_name);
            //copy file into the location
            Path path = Paths.get(AbsoluteFIleLocation);
            Files.copy(file.getInputStream(), path , StandardCopyOption.REPLACE_EXISTING);

            Attributes.addFlashAttribute("message" , "File Uploaded Successfully");

            String authUser = SecurityContextHolder.getContext().getAuthentication().getName();
            Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
            if(optionalAccount.isPresent()){
                Account account = optionalAccount.get();
                String relativeFileLocation = "images/Uploads/"+final_photo_name;
                account.setPhoto(relativeFileLocation);
                accountService.saveAccount(account);
            }try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }

        }catch (Exception e){

        }
        return "redirect:/profile";
    }
}
    


