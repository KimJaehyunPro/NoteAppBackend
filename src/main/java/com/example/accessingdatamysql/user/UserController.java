package com.example.accessingdatamysql.user;

import com.example.accessingdatamysql.user.DTO.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserResponseDTO getUser(Integer userId) {
        return userService.toUserResponseDTO(userService.getUserById(userId));
    }
}