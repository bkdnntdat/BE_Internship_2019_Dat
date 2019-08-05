package com.dat.blog.role;

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
    private int id;

    @Column(nullable = false)
    @NonNull
    private String username;

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

    @Column(nullable = false)
    @NonNull
    private String email;

    @JsonAlias("phone_number")
    @Column(nullable = false)
    @NonNull
    private String phoneNumber;

    @NonNull
    private String avt;

    @ManyToOne
    private Role role;

    private String code;

    @Override
    public String toString() {
        return "Username: " + username + ", Name: " + firstName + lastName + ", Email: " + email + ", Phone number: " + phoneNumber + ", Quyền: " + role.toString();
    }
}
