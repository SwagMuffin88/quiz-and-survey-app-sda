package com.sda.controllers;

import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.services.QuestionService;
import com.sda.services.QuizService;
import lombok.SneakyThrows;
import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question")
public class QuestionController {
    @Autowired
    @Lazy
    private QuestionService questionService;
    @Autowired
    private QuizService quizService;

    @SneakyThrows
    @PostMapping("/create/{quizId}") // Password protected
    public ResponseEntity<Question> createQuestionAndAddToQuiz(@PathVariable long quizId, @RequestBody Question question) {
        Quiz quiz = quizService.findQuizById(quizId);
        Question newQuestion = questionService.createQuestion(question);
        questionService.saveQuestion(newQuestion);
        questionService.addQuestionToQuiz(quizId, newQuestion);
        quizService.editQuiz(quizId, quiz);
        return new ResponseEntity<Question>(newQuestion, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/edit") //Password protected
    public ResponseEntity<Question> editQuestion(@PathVariable long id, @RequestBody Question question) {
        Question updatedQuestion = questionService.editQuestion(id, question);
        return new ResponseEntity<>(updatedQuestion, HttpStatus.OK);
    }

    @PutMapping("/{id}/remove") // Password protected
    public ResponseEntity<String> removeQuestionById(@PathVariable long id) {
        questionService.disableQuestion(id);
        return new ResponseEntity<>(
                "The question with ID " + id + " is removed", HttpStatus.NO_CONTENT);
    }

}
