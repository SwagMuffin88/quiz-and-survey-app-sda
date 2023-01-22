package com.sda.controllers;

import com.sda.model.users.Author;
import com.sda.services.AuthorService;
import com.sda.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class AuthorController {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private QuizService quizService;

    @GetMapping("/{id}")
    public ResponseEntity<Author> getUserById (@PathVariable Long id ){
        Author author = authorService.findAuthorById(id);
        return new ResponseEntity<Author>(author, HttpStatus.OK);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<Author> updateUser (@PathVariable Long id , @RequestBody Author author){
        return authorService.updateAuthor(id, author);
    }

    @PutMapping("/{id}/disable-account")
    public ResponseEntity<String> removeAuthorUser(@PathVariable long id) {
        authorService.disableAuthor(id);
        return new ResponseEntity<String>("The author with ID " + id + " is removed", HttpStatus.NO_CONTENT);
    }

}
