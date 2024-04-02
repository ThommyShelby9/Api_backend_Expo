package com.api.expo.repository;
import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.expo.models.User;

public interface UserRepository extends CrudRepository<User, Integer>{

    UserDetails findByFirstname(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(BigInteger id);
    
}
