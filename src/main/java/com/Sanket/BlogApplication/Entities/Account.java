package com.Sanket.BlogApplication.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    //account id
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    //account FirstName
     @NotEmpty(message = "Enter FirstName")
    private String FirstName;
    //account LastName
     @NotEmpty(message = "Enter LastName")
    private String LastName;
    //account email
    @Email(message = "Please Enter Valid Email")
    @NotEmpty(message = "Enter email")
    private String email;
    //account password
    @NotEmpty(message = "Please Enter Valid Password")
    private String password;
    private String Role;

    private String gender;

    private int age;

    @Column(name = "token")
    private String token;

    private LocalDateTime password_reset_token_expiry;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate Date_Of_Birth;
    
    private String photo;
    //account - post relational mapping
    @OneToMany(mappedBy = "account")
    private List<Post> posts;

    //account - authority relational mapping
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_authority",
            joinColumns = {@JoinColumn(name="account_id" , referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "authority_id" , referencedColumnName = "id")})

    private Set<Authority> authority = new HashSet<>();

    @Override
    public String toString() {
        return "Account [id=" + id + ", FirstName=" + FirstName + ", LastName=" + LastName + ", email=" + email
                + ", password=" + password + ", Role=" + Role + ", gender=" + gender + ", age=" + age
                + ", password_reset_token=" + token + ", password_reset_token_expiry="
                + password_reset_token_expiry + ", Date_Of_Birth=" + Date_Of_Birth + ", photo=" + photo + ", posts="
                + posts + ", authority=" + authority + "]";
    }

    

    
}
