package com.microservices.user.controller;

import com.microservices.user.entity.User;
import com.microservices.user.payload.ErrorDto;
import com.microservices.user.payload.LoginDto;
import com.microservices.user.payload.TokenDto;
import com.microservices.user.payload.UserDto;
import com.microservices.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signUp")
    public ResponseEntity<?> userSignUP(@Valid @RequestBody UserDto userDto,
BindingResult result){
    if(result.hasErrors()){
       return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
    }
        String hashpw = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt(5));
        userDto.setPassword(hashpw);
        User user = userService.addUser(userDto);

    return new ResponseEntity<>(user, HttpStatus.OK);


}
    @GetMapping("/signIn")
    public ResponseEntity<?> userLogin(@RequestBody LoginDto loginDto){
        String token = userService.loginUser(loginDto);
        if(token== null){
            return new ResponseEntity<>("Invalid password", HttpStatus.FORBIDDEN);
        }

        TokenDto jwt = new TokenDto(token, "JWT");
        return new ResponseEntity<>(jwt, HttpStatus.OK);


    }

    @PutMapping("/updateUser")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserDto userDto , @RequestParam String userId,
                                             BindingResult result  ){

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
        User user = userService.updateUser(userDto, userId);


        return new ResponseEntity<>("user updated successfully",HttpStatus.OK);


    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<String> deleteUser(@RequestParam String userId){
        userService.deleteUser(userId);

        return new ResponseEntity<>("user deleted successfully", HttpStatus.OK);
    }
}
