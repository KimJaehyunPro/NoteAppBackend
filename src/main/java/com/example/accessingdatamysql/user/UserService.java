package com.example.accessingdatamysql.user;
import com.example.accessingdatamysql.user.DTO.UserResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Transactional
    public User getUserById(Integer userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            return userOptional.get();
        }

        return null;
    }

    public UserResponseDTO toUserResponseDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getRoles()
        );
    }
}
