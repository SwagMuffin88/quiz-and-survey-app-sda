package com.sda.repositories;


import com.sda.model.users.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Optional<Author> findByEmail(String email);
    Optional<Author> findByUsername(String username);

//    @Query("select a from Author a where a.isAvailable = true")
//    Optional<List<Author>> findAuthorsByIsAvailableIsTrue();
//    @Query("select distinct a from Author a where a.id = ?1 and a.isAvailable = true")
//    Optional<Author> findDistinctByIdAndIsAvailableIsTrue(long id);


}
