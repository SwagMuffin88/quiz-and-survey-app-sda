package com.sda.controllers.authentication;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "First name cannot be empty!")
    @Pattern(regexp = ("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+"),
            message = "The name can only contain letters and the first letter must be uppercase")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty!")
    @Pattern(regexp = ("\\b([A-ZÀ-ÿ][-,a-z. ']+[ ]*)+"),
            message = "The name can only contain letters and the first letter must be uppercase")
    private String lastName;

    @NotBlank(message = "Username cannot be empty!")
    @Column(name = "username", unique=true)
    private String username;

    @Size(min=5 , message = "The password must have at least 5 characters")
    @NotBlank(message = "Password cannot be empty!")
    private String password;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be empty!")
    private String email;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
}
