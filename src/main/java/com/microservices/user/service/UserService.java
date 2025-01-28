package com.microservices.user.service;

import com.microservices.user.entity.User;
import com.microservices.user.excepiton.ResourceNotFound;
import com.microservices.user.payload.LoginDto;
import com.microservices.user.payload.UserDto;
import com.microservices.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private JWTService jwtService;

    public UserService(UserRepository userRepository, JWTService jwtService){
        this.userRepository= userRepository;
        this.jwtService = jwtService;
    }
    public User addUser(UserDto userDto) {

      //  List<User> users = userRepository.findByEmailOrUserName(userDto.email, userDto.username);


        Optional<User> byEmail = userRepository.findByEmail(userDto.email);
        if(byEmail.isPresent()){
            throw new RuntimeException("email is already associated with an account");
        }

        Optional<User> byUsername = userRepository.findByUserName(userDto.username);
        if(byUsername.isPresent()){
          throw new RuntimeException("username is already present in database ");
        }

        String id = UUID.randomUUID().toString().substring(0,7)+userDto.getUsername();

        User user = new User();

        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setUserName(userDto.getUsername());
        user.setPassword(userDto.getPassword());

        user.setRole("ROLE_USER");
        user.setId(id);

       return  userRepository.save(user);

    }

    public String loginUser(LoginDto loginDto) {

        User user = userRepository.findByUserNameOrEmail(loginDto.getUsername()).orElseThrow(
                () -> new ResourceNotFound("Incorrect Username")
        );
        boolean checkpw = BCrypt.checkpw(loginDto.getPassword(), user.getPassword());
        if(checkpw){
           return jwtService.generateToken(user.getUserName());
        }
      return null;
    }

    public User updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFound("Please enter valid user id")
        );
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setUserName(userDto.getUsername());
        user.setPassword(userDto.getPassword());
       return  userRepository.save(user);
    }

    public void deleteUser(String userId) {

        User provideValidUserId = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFound("Provide valid user Id")
        );

        userRepository.deleteById(userId);

    }
}
