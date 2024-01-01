package com.Sanket.BlogApplication.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.Sanket.BlogApplication.Entities.Post;
import com.Sanket.BlogApplication.Repository.PostRepo;

@Service
public class PostService {
 
    @Autowired
    private PostRepo postrepo;

    //getbyid
    public Optional<Post> getPostById(Long id){
        return postrepo.findById(id);
    }
    //getall
    public List<Post> getAllPost(){
        return postrepo.findAll();
    }
    //save 
    public Post savePost(Post post){
        //the save method saves the post and if there is no id of post then we will set its time as current time.
        if(post.getId() == null){
            post.setCreatedAt(LocalDateTime.now());
        }
        post.setUpdatedAt(LocalDateTime.now());
        return postrepo.save(post);
    }
    //delete
    public void delete(Post post){
        postrepo.delete(post);
    }
}
