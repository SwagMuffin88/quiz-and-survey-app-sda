package com.sda.services;

import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.users.Author;
import com.sda.repositories.AuthorRepository;
import com.sda.repositories.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final QuizRepository quizRepository;
    private final PasswordEncoder passwordEncoder;

    public Author findAuthorById(Long id) {
        // Finds all authors in the database regardless of availability
        Optional<Author> author = authorRepository.findById(id);
        return author.orElseThrow(()-> new ResourceNotFoundException("The user with the ID " + id + " not found "));
    }

    public Author findUserByUserName(String username) {
        return authorRepository.findByUsername(username).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
    }

    public void saveNewAuthor(Author author) {
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        author.setAvailable(true);
        authorRepository.save(author);
    }

    public ResponseEntity<Author> updateAuthor( Long id, Author author) {
        // Finds and updates author entity regardless of availability
        // Ideally, disabled users should not be modifiable.
        Author authorToUpdate = authorRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("The user with the ID "+id+" not found "));
        authorToUpdate.setLastName(author.getLastName());
        authorToUpdate.setFirstName(author.getFirstName());
        authorToUpdate.setPassword(author.getPassword());
        authorToUpdate.setUsername(author.getUsername());
        authorToUpdate.setAvailable(true);
//        authorToUpdate.setDateOfBirth(author.getDateOfBirth());
        authorRepository.saveAndFlush(authorToUpdate);
        return new ResponseEntity<Author>(authorToUpdate, HttpStatus.OK);

    }

    public void disableAuthor(long authorId) {
        Author author = findAuthorById(authorId);
        author.setAvailable(!author.isAvailable());
    }

    // Method for recovering account
    // Look for specific user in database, set availability to true
//     List<Author> removedAuthors = new ArrayList<>();
    // Author author = authorRepository.findAll()
    // if author.isAvailable() = true {
    //  removedAuthors.add(author)...

    // Should be removed once project is finished
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }


}
