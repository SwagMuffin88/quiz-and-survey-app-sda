package com.sda.services;

import com.sda.impl.CustomUserDetails;
import com.sda.model.users.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthorService authorService;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Author author = authorService.findUserByUserName(username);
        return new CustomUserDetails(author);
    }
}
