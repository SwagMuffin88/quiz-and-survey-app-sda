package com.sda.services;

import com.sda.config.JwtService;
import com.sda.controllers.authentication.AuthenticationRequest;
import com.sda.controllers.authentication.AuthenticationResponse;
import com.sda.controllers.authentication.RegisterRequest;
import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.users.Author;
import com.sda.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service @RequiredArgsConstructor
public class AuthenticationService {
    private final AuthorRepository authorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        //Allows us to create a user, save it to a DB and return a generated token out of it
        var author = Author.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .dateOfBirth(request.getDateOfBirth())
                .isAvailable(true)
                .build();
        authorRepository.save(author);
        var jwtToken = jwtService.generateToken(author);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()));
         // In case the username or password are not correct, an exception will be thrown.
        // ***
        // If we get to this point, we know the user has been authenticated. (Correct)
        // Now we need to generate a token and send it back.
        var author = authorRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        var jwtToken = jwtService.generateToken(author); // generate token based on user info
        return AuthenticationResponse.builder()     // return a response based on token
                .token(jwtToken)
                .userId(author.getId())
                .build();
    }
}
