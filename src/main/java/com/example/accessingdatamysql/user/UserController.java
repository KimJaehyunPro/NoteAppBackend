package com.example.accessingdatamysql.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public AddNewUserResponseDTO addNewUser (
            @RequestBody
            AddNewUserRequestDTO addNewUserRequestDTO) {

        return userService.createNewUser(addNewUserRequestDTO.getUsername(), addNewUserRequestDTO.getEmail());

    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
}