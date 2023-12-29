package com.Sanket.BlogApplication.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank(message = "Enter Post Title")
    private String title;
    @NotBlank(message = "Enter Post Body")
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    //map relationship for each post
    @ManyToOne
    @JoinColumn(name = "account_id" , referencedColumnName = "id" , nullable = true)
    private Account account;
}
