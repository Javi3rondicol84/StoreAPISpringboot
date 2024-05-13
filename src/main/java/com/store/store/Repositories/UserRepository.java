package com.store.store.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.store.Entities.User;

public interface UserRepository extends JpaRepository<User, Long>{
    
}
