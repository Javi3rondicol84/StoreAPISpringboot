package com.store.store.Controllers;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.Helpers.HttpHelper;
import com.store.store.Entities.User;
import com.store.store.Repositories.UserRepository;


@RestController
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    private HttpHelper httpHelper;

    private HashSet<String> usernames;

    public UserController() {
        this.httpHelper = new HttpHelper();
        this.usernames = new HashSet<>();
    }

    @GetMapping(value = {"/users/", "/users"})
    public ResponseEntity<?> getAllUsers() {
        List<User> users = this.userRepository.findAll();

        return this.httpHelper.getAllItemsResponse(users);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = this.userRepository.findById(id).orElse(null);

        return this.httpHelper.getItemByIdResponse(user, id);
    }

    @PostMapping(value = {"/users/add", "/users/add/"})
    public ResponseEntity<?> createUser(@RequestBody User user) {
        boolean userExists = false;
        if(user != null) {
            String userName = user.getUserName();
            userExists = this.usernameAlreadyExists(userName);
        
            if(!userExists) {
                this.userRepository.save(user);
                this.usernames.add(userName);
            }
            else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
            }    
            
        }

        return this.httpHelper.getPostResponse(user);
    }

    @PutMapping("/users/update/{id}")
    public ResponseEntity<?> updateUpdate(@RequestBody User newUser, @PathVariable Long id) {

        //corregir tema id
        User oldUser = this.userRepository.findById(id).orElse(null);
        boolean userExists = false;
        
        if(oldUser != null) {

            String userName = newUser.getUserName();
            userExists = this.usernameAlreadyExists(userName);

            if(!userExists) {
                oldUser.setUserName(newUser.getUserName());
                oldUser.setEmail(newUser.getEmail());
                oldUser.setPassword(newUser.getPassword());

                this.userRepository.save(oldUser);
                //ACTUALIZAR hashset this.usernames.
            }
            else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya existe");
            }

        }

        return this.httpHelper.getPutResponse(oldUser, id);
    }

    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteCalendar(@PathVariable Long id) {
        User user = this.userRepository.findById(id).orElse(null);

        if(user != null) {
            this.userRepository.delete(user);
        }

        return this.httpHelper.getDeleteResponse(user, id);
    }

    private boolean usernameAlreadyExists(String username) {
        return this.usernames.contains(username);
    }



}
