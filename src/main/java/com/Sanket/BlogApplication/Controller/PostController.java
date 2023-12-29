package com.Sanket.BlogApplication.Controller;
import com.Sanket.BlogApplication.Entities.Account;
import com.Sanket.BlogApplication.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.Sanket.BlogApplication.Entities.Post;
import com.Sanket.BlogApplication.Services.PostService;

import jakarta.validation.Valid;

import java.security.Principal;
import java.util.Optional;

@Controller

public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/posts/{id}")
    public String getPostById(@PathVariable Long id, Model model,Principal principal) {
        //find post by id
        Optional<Post> Optional_post = postService.getPostById(id);

        //retrive authorize usersname
        String authUser = SecurityContextHolder.getContext().getAuthentication().getName();
        //if post is present then add it
        if (Optional_post.isPresent()) {
            Post post = Optional_post.get();
            model.addAttribute("post", post);
            if(principal != null){
                authUser = principal.getName();
            }
            //allow editing the post
            //For editing the post we must verify whether the name is valid
            if (authUser.equals(post.getAccount().getEmail())) {
                model.addAttribute("isOwner", true);
            } else {
                model.addAttribute("isOwner", false);
            }

            return "post_view/posts";
        }
        else{
            return "404";
        }
    }

    @GetMapping("/posts/add")
    @PreAuthorize("isAuthenticated()")
    public String addPost(Model model, Principal principal) {
        String authUser = "email";
        if(principal != null){
            authUser = principal.getName();
        }
        Optional<Account> optionalAccount = accountService.findOneByEmail(authUser);
        if(optionalAccount.isPresent()){
            Post post = new Post();
            post.setAccount(optionalAccount.get());
            model.addAttribute("post" , post);
            return "post_view/post_add";
        }
        else{
            return "home_view/home";
        }
    }

    @PostMapping("/posts/add")
    @PreAuthorize("isAuthenticated()")
    public String addPostHandler(@Valid @ModelAttribute Post post,BindingResult result ,Principal principal ){
        if(result.hasErrors()){
            return "post_view/post_add";
        }
        String authUser = "email";
        if(principal != null){
            authUser = principal.getName();
        }
        if (post.getAccount().getEmail().compareToIgnoreCase(authUser) < 0){
            return "redirect:/?error";
        }
        postService.savePost(post);
        //redirecting to the post
        return "redirect:/posts/"+post.getId();
    }

    @GetMapping("/posts/edit/{id}")
    public String getPostForEdit(@PathVariable Long id , Model model) {
        //find post by id
       Optional<Post> optionalPost = postService.getPostById(id);
       if(optionalPost.isPresent()){
        //modify post and add it
        Post post = optionalPost.get();
        model.addAttribute("post", post);
        return "post_view/post_Edit";
       }
       return "post_view/postNotFound";
    }

    @PostMapping("/posts/edit/{id}")
    @PreAuthorize("isAuthenticated()")

    public String editPost(@Valid @ModelAttribute Post post ,BindingResult bindingResult ,@PathVariable Long id){
        if(bindingResult.hasErrors()){
            return "post_view/post_Edit";
        }
        
        Optional<Post> optionalPost = postService.getPostById(id);
        if(optionalPost.isPresent()){
            //get existing post
            Post UpdatedPost = optionalPost.get();
            UpdatedPost.setTitle(post.getTitle());
            UpdatedPost.setBody(post.getBody());
            //save post
            postService.savePost(UpdatedPost);
        }
        return "redirect:/posts/"+post.getId();
    }
    @GetMapping("/posts/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public String deletepost(@PathVariable Long id){
         Optional<Post> optionalPost = postService.getPostById(id);
         if(optionalPost.isPresent()){
            Post post = optionalPost.get();
            postService.delete(post);
            return "redirect:/home";
        }
       else{
         return "404";
       }
    }
}
