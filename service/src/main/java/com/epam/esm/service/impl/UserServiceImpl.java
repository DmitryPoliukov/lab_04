package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.UserDao;
import com.epam.esm.repository.dto.UserDto;
import com.epam.esm.repository.entity.User;
import com.epam.esm.service.UserService;
import com.epam.esm.service.exception.ResourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
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
}
