package com.epam.esm.service.impl;

import com.epam.esm.repository.dao.impl.UserDaoImpl;
import com.epam.esm.repository.dto.UserDto;
import com.epam.esm.repository.entity.Role;
import com.epam.esm.repository.entity.User;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class UserServiceImplTest {
    private UserDaoImpl userDao = Mockito.mock(UserDaoImpl.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

    private UserService userService = new UserServiceImpl(userDao, bCryptPasswordEncoder);

    private static final User USER_1 = new User(1, "name1", "sname1", "email1", "$2a$10$GOvB8CJNN3gz8j96VDnAmuAyFhWpyHafyxXXRVAB.b/hndkSnbB8G", Role.USER);
    private static final User USER_2 = new User(2, "name2", "sname2", "email2", "$2a$10$GOvB8CJNN3gz8j96VDnAmuAyFhWpyHafyxXXRVAB.b/hndkSnbB8G", Role.USER);
    private static final User USER_3 = new User(3, "name3", "sname3", "email3", "$2a$10$GOvB8CJNN3gz8j96VDnAmuAyFhWpyHafyxXXRVAB.b/hndkSnbB8G", Role.USER);

    private static final int PAGE = 0;
    private static final int SIZE = 5;

    @Test
    void read() {
        when(userDao.read(USER_1.getId())).thenReturn(Optional.of(USER_1));

        User actual = userService.read(USER_1.getId()).toEntity();
        User expected = USER_1;

        assertEquals(expected, actual);
    }

    @Test
    void readAll() {
        List<User> users = Arrays.asList(USER_1, USER_2, USER_3);
        when(userDao.readAll(PAGE, SIZE)).thenReturn(users);

        List<User> actual = userService.readAll(PAGE, SIZE).stream()
                .map(UserDto::toEntity).collect(Collectors.toList());
        List<User> expected = users;

        assertEquals(expected, actual);
    }


    @Test
    void saveUser() {
        when(userDao.saveUser(USER_1)).thenReturn(USER_1);

        User actual = userService.saveUser(USER_1.toDto()).toEntity();
        User expected = USER_1;

        assertEquals(expected, actual);
    }

    @Test
    void findByEmail() {
        when(userDao.findByEmail(USER_2.getEmail())).thenReturn(Optional.of(USER_2));

        User actual = userService.findByEmail(USER_2.getEmail()).toEntity();
        User expected = USER_2;

        assertEquals(expected, actual);
    }
}