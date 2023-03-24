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

}
