package com.Sanket.BlogApplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Sanket.BlogApplication.Entities.Post;

@Repository
public interface PostRepo extends JpaRepository<Post , Long> {
    
}
