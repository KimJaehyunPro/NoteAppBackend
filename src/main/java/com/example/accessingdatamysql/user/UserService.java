package com.example.accessingdatamysql.user;

import org.springframework.stereotype.Service;

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

    public Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}
