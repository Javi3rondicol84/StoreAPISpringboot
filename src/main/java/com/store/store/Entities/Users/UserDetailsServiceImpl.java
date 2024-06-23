package com.store.store.Entities.Users;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.store.store.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findUserEntityByUserName(username);

        if (userEntity == null) {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

      // Agregar roles como autoridades
    for (RoleEntity role : userEntity.getRoles()) {
        authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()));

        // Agregar permisos como autoridades
        for (PermissionEntity permission : role.getPermissions()) {
            authorityList.add(new SimpleGrantedAuthority(permission.getName()));
        }
    }

        logger.debug("it works");

        return new User(userEntity.getUserName(), 
        userEntity.getPassword(), 
        userEntity.isEnabled(), 
        userEntity.isAccountNoExpired(),
        userEntity.isCredentialNoExpired(),
        userEntity.isAccountNoLocked(), 
        authorityList);
    }
    
}
