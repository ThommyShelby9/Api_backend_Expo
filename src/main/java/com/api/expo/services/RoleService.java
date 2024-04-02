package com.api.expo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.expo.models.Role;
import com.api.expo.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createRole(Role role) {
        return roleRepository.save(role);
}

    public Role getRole(Long id) {
        return roleRepository.findById(id).orElseThrow();
    }

    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }
}

