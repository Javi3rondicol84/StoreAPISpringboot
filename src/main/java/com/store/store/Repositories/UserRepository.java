package com.store.store.Repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.store.store.Entities.Users.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    
    public UserEntity findUserEntityByUserName(String userName);
}
