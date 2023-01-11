package com.example.accessingdatamysql.user;

import com.example.accessingdatamysql.user.DTO.AddNewUserResponseDTO;
import com.example.accessingdatamysql.user.DTO.GetAllUserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AddNewUserResponseDTO createNewUser(
            String username,
            String email
    ) {
        User newUser = new User();
        newUser.setName(username);
        newUser.setEmail(email);
        userRepository.save(newUser);

        AddNewUserResponseDTO newUserResponse = new AddNewUserResponseDTO(
                newUser.getId(),
                newUser.getName(),
                newUser.getEmail());

        return newUserResponse;
    }

    public List<GetAllUserResponseDTO> getAllUsers() {

        Iterable<User> allUsersConfidential = userRepository.findAll();
        List<GetAllUserResponseDTO> allUsersResponse = new ArrayList<>();

        for (User user : allUsersConfidential) {
            GetAllUserResponseDTO getAllUserResponseDTO = new GetAllUserResponseDTO(user.getId(), user.getName(), user.getEmail());
            allUsersResponse.add(getAllUserResponseDTO);
        }

        return allUsersResponse;
    }
}
