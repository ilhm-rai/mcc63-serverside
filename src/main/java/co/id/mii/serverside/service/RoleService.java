/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.Role;
import co.id.mii.serverside.repository.RoleRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author RAI
 */
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getById(Long id) {
        return roleRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not Found")
        );
    }

    public Role create(Role role) {
        if (role.getId() != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role already exist");
        }

        if (roleRepository.findByName(role.getName()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Role name already exist");
        }
        return roleRepository.save(role);
    }

    public Role update(Long id, Role role) {
        Role getRole = getById(id);
        if (!getRole.getName().equalsIgnoreCase(role.getName())) {
            if (roleRepository.findByName(role.getName()) != null) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Role name already exist");
            }
        }
        role.setId(id);
        return roleRepository.save(role);
    }

    public Role delete(Long id) {
        Role role = getById(id);
        roleRepository.delete(role);
        return role;
    }
}
