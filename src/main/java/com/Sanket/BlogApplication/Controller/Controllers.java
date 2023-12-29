package com.Sanket.BlogApplication.Controller;

import com.Sanket.BlogApplication.Services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class Controllers {

    @Autowired
    private PostService postService;
    @GetMapping("/home")
    public String home(Model Model){
        Model.addAttribute("posts" ,postService.getAllPost());
        return "home_view/home";
    }
    @GetMapping("/about")
    public String about(Model model){
        return "about";
    }

}
