package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.dto.UserDto;
import com.epam.esm.repository.entity.Role;
import com.epam.esm.repository.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.IncorrectParameterException;
import com.epam.esm.service.exception.NoSuchEntityException;
import com.epam.esm.service.exception.ResourceException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncode;

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncode) {
        this.userDao = userDao;
        this.bCryptPasswordEncode = bCryptPasswordEncode;
    }

    @Override
    public UserDto read(int id) {
        Optional<User> user = userDao.read(id);
        return user.orElseThrow(ResourceException.notFoundWithUser(id)).toDto();
    }

    @Override
    public List<UserDto> readAll(int page, int size) {
        List<User> users = userDao.readAll(page, size);
        return users.stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto saveUser(UserDto userDto) {
        if (userDto == null) {
            throw new IncorrectParameterException("Null parameter in save user");
        }

        boolean isUserExist = userDao.findByEmail(userDto.getEmail()).isPresent();
        if (isUserExist) {
            throw new IncorrectParameterException("User with such email is exist");
        }

        userDto.setPassword(bCryptPasswordEncode.encode(userDto.getPassword()));
        userDto.setRole(Role.USER);
        return userDao.saveUser(userDto.toEntity()).toDto();
    }

    @Override
    public UserDto findByEmail(String email) {
        if (email == null) {
            throw new IncorrectParameterException("Null parameter in load user by username");
        }

        Optional<User> user = userDao.findByEmail(email);
        if (user.isPresent()) {
            return user.get().toDto();
        } else {
            throw new NoSuchEntityException("No user with such email");
        }
    }
}
