package com.epam.esm.controller;

import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.repository.dto.UserDto;
import com.epam.esm.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Class {@code UserController} is an endpoint of the API which allows to perform operations on users.
 * Annotated by {@link RestController} with no parameters to provide an answer in application/json.
 * Annotated by {@link RequestMapping} with parameter value = "/users".
 * So that {@code UserController} is accessed by sending request to /users.
 *
 * @author Dmitry Poliukov
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final HateoasAdder<UserDto> userHateoasAdder;

    public UserController(UserService userService, HateoasAdder<UserDto> userHateoasAdder) {
        this.userService = userService;
        this.userHateoasAdder = userHateoasAdder;
    }

    /**
     * Method for getting user by ID.
     *
     * @param id ID of user
     * @return Found user entity with hateoas
     */
    @GetMapping("/{id}")
    public UserDto read(@PathVariable int id) {
        UserDto userDto = userService.read(id);
      userHateoasAdder.addLinks(userDto);
        return userDto;
    }

    /**
     * Method for getting all users from data source.
     *
     * @param page the number of page for pagination
     * @param size the size of page for pagination
     * @return List of found users with hateoas
     */
    @GetMapping
    public List<UserDto> readAll(@RequestParam(value = "page", defaultValue = "1", required = false) @Min(1) int page,
                                  @RequestParam(value = "size", defaultValue = "5", required = false) @Min(1) int size) {
        List<UserDto> userDtos = userService.readAll(page, size);
        userDtos.forEach(userHateoasAdder::addLinks);
        return userDtos;

    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto saveUser(@Valid @RequestBody UserDto userDto) {
        UserDto savedUser = userService.saveUser(userDto);
        userHateoasAdder.addLinks(savedUser);
        return savedUser;
    }
}
