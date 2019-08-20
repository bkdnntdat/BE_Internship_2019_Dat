package com.dat.book_management.configurations;

public class UserAuthenticationFail extends RuntimeException{
    public UserAuthenticationFail(String message){
        super(message);
    }

}
