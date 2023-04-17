package com.example.accessingdatamysql.authentication.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
