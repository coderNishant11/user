package com.microservices.user.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public class UserDto {
    @Size(min=4, max = 25, message = "name should be in between 4 to 25")
    public String name;

    @Size(min=4, max = 25, message = "username should be in between 4 to 25")
    public String username;

    @Size(min=4, max = 25, message = "password should be in between 4 to 25")
    public String password;

    @Email
    public String email;

    public UserDto(String name, String username, String password, String email){
        this.name= name;
        this.username=username;
        this.password=password;
        this.email=email;
    }
    public UserDto(){

    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }


    public String getUsername(){
        return username;

    }

    public void setUsername(String username){
        this.username = username;

    }

    public String getPassword(){
        return password;

    }

    public void setPassword(String password){
        this.password = password;
    }
}
