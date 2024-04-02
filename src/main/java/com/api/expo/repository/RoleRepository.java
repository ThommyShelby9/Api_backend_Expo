package com.api.expo.repository;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.api.expo.models.Role;
import com.api.expo.models.User;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<User> findById(Role roleId);
}

