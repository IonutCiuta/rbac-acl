package com.ionut.ciuta.posd1.repository;

import com.ionut.ciuta.posd1.model.sql.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByName(String name);
}
