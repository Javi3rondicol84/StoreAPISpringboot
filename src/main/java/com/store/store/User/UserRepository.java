package com.store.store.User;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>{
    
    public Optional<User> findUserEntityByUserName(String userName);
}