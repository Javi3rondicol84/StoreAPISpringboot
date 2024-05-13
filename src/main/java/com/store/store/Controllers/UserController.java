package com.store.store.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.store.store.Repositories.UserRepository;

@RestController
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    

}
