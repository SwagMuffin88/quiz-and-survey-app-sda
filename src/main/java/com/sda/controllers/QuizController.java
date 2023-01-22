package com.sda.controllers;

import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.services.AuthorService;
import com.sda.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    @Autowired
    AuthorService authorService;
    @Autowired
    QuizService quizService;

    @PostMapping("/create/{userId}") // Password protected
    public ResponseEntity<Quiz> createQuizForAuthor(@PathVariable Long userId, @RequestBody Quiz quiz) {
        System.out.println(quiz.isPublic());
        // Create quiz entity
        Quiz newQuiz = quizService.createQuizAndAddAuthor(quiz ,userId);  // Method assigns author to quiz
        //Save the quiz entity
        quizService.saveQuiz(newQuiz);
        return new ResponseEntity<Quiz>(newQuiz, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/edit") // Password protected
    public ResponseEntity<Quiz> editQuizById(@PathVariable long id, @RequestBody Quiz quiz) throws Exception {
            Quiz updatedQuiz = quizService.editQuiz(id, quiz);
            return new ResponseEntity<>(updatedQuiz, HttpStatus.OK);
    }

    @PutMapping("/{id}/remove") // Password protected
    public ResponseEntity<String> removeQuizById(@PathVariable long id) {
        quizService.disableQuiz(id);
        return new ResponseEntity<String>("The quiz with ID " + id + " is removed", HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}") // Accessible to all
    public ResponseEntity<Quiz> getQuizById(@PathVariable long id){
        Quiz quiz = quizService.findQuizById(id);
        //This is to shuffle the answers
        List<Question> questionList= quiz.getQuestions();
        for (Question q :questionList) {
            Collections.shuffle(q.getAnswers());
        }
        return new ResponseEntity<>(quiz, HttpStatus.OK);
    }

    @GetMapping("/userQuizzes/{userId}") // Password protected
    public ResponseEntity<List<Quiz>> getAllUserQuizzes(@PathVariable long userId){
        List<Quiz> quizzes = quizService.getAllQuizzes();
        quizzes.removeIf(quiz -> quiz.getAuthor().getId()!= userId);
        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/all-quizzes") // Should be accessible to all
    public ResponseEntity<List<Quiz>> getAll(){
        List<Quiz> quizzes = quizService.getAllQuizzes();
        // Filter quizzes by availability
        List<Quiz> availableQuizzes = new ArrayList<>();
        for (Quiz q : quizzes) {
            if (q.isAvailable()) availableQuizzes.add(q);
        }
        availableQuizzes.removeIf(quiz -> !quiz.isPublic());
        return new ResponseEntity<>(availableQuizzes, HttpStatus.OK);
    }

}
