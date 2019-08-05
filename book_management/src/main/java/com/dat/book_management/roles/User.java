package com.dat.book_management.roles;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    @NonNull
    private String email;

    @Column(nullable = false)
    @NonNull
    private String password;

    @JsonAlias("first_name")
    @Column(nullable = false)
    @NonNull
    private String firstName;

    @JsonAlias("last_name")
    @Column(nullable = false)
    @NonNull
    private String lastName;

    private String avt;

    @ManyToOne
    private Role role;

    private String code;
}

