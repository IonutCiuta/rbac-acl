package com.ionut.ciuta.posd1.repository;

import com.ionut.ciuta.posd1.model.sql.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByName(String name);
}
