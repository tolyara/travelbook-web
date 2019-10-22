package com.Kh033Java.travelbook.controller;

import com.Kh033Java.travelbook.entity.Role;
import com.Kh033Java.travelbook.entity.User;
import com.Kh033Java.travelbook.exception.NotFoundException;
import com.Kh033Java.travelbook.repository.UserRepository;
import com.Kh033Java.travelbook.service.UserService;
import com.Kh033Java.travelbook.userDetails.requestUserDetails.RequestDetail;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserRepository userRepository;
    private final UserService userService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping(value = "/allUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get all users", consumes = "application/json")
    public Iterable<User> getAllUsers() {
        LOG.info("get all users");
        return userService.getAllUsers();
    }

    @GetMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "get user by login", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "login", required = true, value = "user unique identifier"),
    })
    public User getUser(@PathVariable("login") final String login) {

        LOG.info("get use by login {}", login);

        User user = userService.getUser(login);
        if(user==null){
            throw new NotFoundException("user with this login not found");
        }
        return user;
    }


    @PostMapping(value = "/registration", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "create user", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", required = true, value = "user entity"),
    })
    public User createUser(@RequestBody final User user) {
        LOG.info("Create user {}", user);
        System.out.println("create user " + user);
        return userService.saveUser(user);
    }



    @PutMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "update user by login", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "login", required = true, value = "user unique identifier"),
            @ApiImplicitParam(name = "user", required = true, value = "user entity"),
    })
    public User updateUser(@PathVariable("login") final String login, @RequestBody final RequestDetail user) {
        LOG.info("update user by login {}, with user {} params", login, user);
        return userService.updateUser(login, user);
    }


    @DeleteMapping(value = "/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "remove user", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "login", required = true, value = "user unique identifier"),
    })
    public void deleteUser(@PathVariable("login") final String login) {
        LOG.info("Removing user with login {}", login);
        userService.deleteUser(login);
    }

    @PutMapping(value = "/admin/{login}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "update role of user", consumes = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "login", required = true, value = "user unique identifier"),
            @ApiImplicitParam(name = "role", required = true, value = "user unique role")
    })
    public void setRole(@PathVariable final String login,@RequestBody final Role role){
        userService.setRole(login, role);
    }
}