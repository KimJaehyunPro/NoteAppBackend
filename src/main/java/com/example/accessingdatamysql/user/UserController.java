package com.example.accessingdatamysql.user;

import com.example.accessingdatamysql.user.DTO.AddNewUserRequestDTO;
import com.example.accessingdatamysql.user.DTO.AddNewUserResponseDTO;
import com.example.accessingdatamysql.user.DTO.GetAllUserResponseDTO;
import com.example.accessingdatamysql.user.DTO.DeleteUserRequestDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(
            UserService userService,
            UserRepository userRepository
    ) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping(path="/add") // Map ONLY POST Requests
    public AddNewUserResponseDTO addNewUser (
            @RequestBody
            AddNewUserRequestDTO addNewUserRequestDTO) {

        return userService.createNewUser(addNewUserRequestDTO.getUsername(), addNewUserRequestDTO.getEmail());
    }

    @DeleteMapping(path="/delete")
    public boolean deleteUser(
            @RequestBody
            DeleteUserRequestDTO deleteUserRequestDTO
    ) {
        Optional<User> userOptional = userRepository.findByName(deleteUserRequestDTO.getUsername());
        if (userOptional.isEmpty()) {
            return false;
        }

        User user = userOptional.get();
        userRepository.delete(user);
        return true;
    }

    @GetMapping(path="/all")
    public List<GetAllUserResponseDTO> getAllUsers() {
        return userService.getAllUsers();
    }
}