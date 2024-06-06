package com.store.store.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.store.Entities.ClientUser;

public interface UserRepository extends JpaRepository<ClientUser, Long>{
    
}
