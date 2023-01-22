package com.sda.repositories;

import com.sda.model.quizzes.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    Quiz findByQuizTitle(String quizTitle);
//    @Query("select q from Quiz q where q.isAvailable = true")
//    List<Quiz> findQuizzesByIsAvailableIsTrue();
//
//    @Query("select distinct q from Quiz q where q.quizId = ?1 and q.isAvailable = true")
//    Optional<Quiz> findDistinctByQuizIdAndIsAvailableIsTrue(long id);
}
