package com.example.accessingdatamysql.user;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Integer getId(String username) {
        Optional<User> userIdOptional = userRepository.findByUsername(username);
        if (userIdOptional.isPresent()) {
            return userIdOptional.get().getId();
        }
        return null;
    }

}
