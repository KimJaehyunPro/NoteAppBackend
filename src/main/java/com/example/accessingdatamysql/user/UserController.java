package com.example.accessingdatamysql.user;

import com.example.accessingdatamysql.user.DTO.AddNewUserRequestDTO;
import com.example.accessingdatamysql.user.DTO.AddNewUserResponseDTO;
import com.example.accessingdatamysql.user.DTO.GetAllUserResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<GetAllUserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}