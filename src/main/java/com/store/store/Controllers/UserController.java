package com.store.store.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.store.Helpers.HttpHelper;
import com.store.store.Entities.User;
import com.store.store.Repositories.UserRepository;

@RestController
public class UserController {
    
    @Autowired
    private UserRepository userRepository;
    private  HttpHelper httpHelper;

    public UserController() {
        this.httpHelper = new HttpHelper();
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
        if(user != null) {
            this.userRepository.save(user);
        }

        return this.httpHelper.getPostResponse(user);
    }

}
