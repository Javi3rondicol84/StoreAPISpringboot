package com.store.store.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.store.store.Entities.CartEntity;

public interface CartRepository extends JpaRepository<CartEntity, Long>{
    
}
