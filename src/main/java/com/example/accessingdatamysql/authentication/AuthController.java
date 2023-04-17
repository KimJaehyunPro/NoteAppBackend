package com.example.accessingdatamysql.authentication;

import com.example.accessingdatamysql.authentication.DTO.AuthResponseDTO;
import com.example.accessingdatamysql.authentication.DTO.LoginDTO;
import com.example.accessingdatamysql.authentication.DTO.RegisterDTO;
import com.example.accessingdatamysql.security.JWTGenerator;
import com.example.accessingdatamysql.user.UserEntity;
import com.example.accessingdatamysql.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    private JWTGenerator jwtGenerator;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JWTGenerator jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                loginDTO.getUsername(),
                loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerator.generateToken(authentication);

        return new ResponseEntity<>(new AuthResponseDTO(token), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            return new ResponseEntity<>("Username is taken!", HttpStatus.BAD_REQUEST);
        }

        if (registerDTO.getPassword().equals(registerDTO.getConfirmation()) == false) {
            return new ResponseEntity<>("Wrong confirmation. Check the password and type in correct confirmation.", HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));

        Role roles = roleRepository.findByName("USER").get();
        user.setRoles(Collections.singletonList(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }
}
