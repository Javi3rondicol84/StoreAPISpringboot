package com.store.store.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.store.Entities.Users.UserDetailsServiceImpl;
import com.store.store.Entities.Users.UserEntity;
import com.store.store.Helpers.HttpHelper;
import com.store.store.Repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class UserController {
     private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    private HttpHelper httpHelper;


    public UserController() {
        this.httpHelper = new HttpHelper();
    }

    @GetMapping(value = {"/users/", "/users"})
    public ResponseEntity<?> getAllUsers() {
        List<UserEntity> users = this.userRepository.findAll();

        return this.httpHelper.getAllItemsResponse(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        UserEntity user = this.userRepository.findById(id).orElse(null);

        return this.httpHelper.getItemByIdResponse(user, id);
    }

    @PostMapping(value = {"/users/add", "/users/add/"})
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        boolean userExists = false;
        if(user != null) {
            String userName = user.getUserName();
            userExists = this.usernameAlreadyExists(userName);
        
            if(!userExists) {
             String encodedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encodedPassword);
                user.setAccountNoExpired(true);
                user.setAccountNoLocked(true);
                user.setCredentialNoExpired(true);
                user.setEnabled(true);
                this.userRepository.save(user);
            }
            else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
            }    
            
        }
        else {
            
        }

        return this.httpHelper.getPostResponse(user);
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<?> updateUpdate(@RequestBody UserEntity newUser, @PathVariable Long id) {

        //corregir tema id
        UserEntity oldUser = this.userRepository.findById(id).orElse(null);
        boolean userExists = false;
        
        if(oldUser != null) {

            String userName = newUser.getUserName();
            userExists = this.usernameAlreadyExists(userName);

            if(!userExists) {
                oldUser.setUserName(newUser.getUserName());
                oldUser.setEmail(newUser.getEmail());

              String encodedPassword = passwordEncoder.encode(newUser.getPassword());
                oldUser.setPassword(encodedPassword);

                oldUser.setPassword(newUser.getPassword());

                this.userRepository.save(oldUser);
            }
            else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
            }

        }

        return this.httpHelper.getPutResponse(oldUser, id);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteCalendar(@PathVariable Long id) {
        UserEntity user = this.userRepository.findById(id).orElse(null);

        if(user != null) {
            this.userRepository.delete(user);
        }

        return this.httpHelper.getDeleteResponse(user, id);
    }

    private boolean usernameAlreadyExists(String username) {
        return this.userRepository.findUserEntityByUserName(username) != null;
    }

    @PostMapping({"/users/exists", "/users/exists/"})
    public ResponseEntity<?> userExists(@RequestBody UserEntity user) {

//this.usernames.contains(user.getUserName()) && 

        if(this.passwordIsCorrect(user.getUserName(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body("El usuario existe");
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El usuario no existe o la contraseña es incorrecta");
    }
    
    private boolean passwordIsCorrect(String username, String password) {
        UserEntity user = this.userRepository.findUserEntityByUserName(username);
        if(user == null) {
            return false;
        }
        
        if(this.passwordEncoder.matches(password, user.getPassword())) {
           // logger.debug("sonnnnn igualeeeeeees");
            return true;
        }


        return false;
    }

}