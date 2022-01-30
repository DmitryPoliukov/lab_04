package com.epam.esm.repository.dao;

import com.epam.esm.repository.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> read(int id);

    List<User> readAll(int page, int size);

    User saveUser(User user);

    Optional<User> findByEmail(String email);

}
