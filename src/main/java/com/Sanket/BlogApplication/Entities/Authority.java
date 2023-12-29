package com.Sanket.BlogApplication.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Authority {

    @Id
    private Long id;
    private String name;
}
