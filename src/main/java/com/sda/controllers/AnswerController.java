package com.sda.controllers;

import com.sda.model.quizzes.Answer;
import com.sda.model.quizzes.Question;
import com.sda.services.AnswerService;
import com.sda.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer")
public class AnswerController {
    @Autowired
    private AnswerService answerService;
    @Autowired
    private QuestionService questionService;

    @PostMapping("/create/{questionId}")
    public ResponseEntity<Answer> createAnswerAndAddToQuestion(@PathVariable long questionId, @RequestBody Answer answer) {
        Question question = questionService.findQuestionById(questionId);
        Answer newAnswer = answerService.createAnswer(answer);
        answerService.saveAnswer(newAnswer);
        answerService.addAnswerToQuestion(questionId, newAnswer);
        return new ResponseEntity<Answer>(newAnswer, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<Answer> editAnswer(@PathVariable long id, @RequestBody Answer answer) {
        return new ResponseEntity<>(answerService.editAnswer(id, answer), HttpStatus.OK);
    }

    @PutMapping("/{id}/remove")
    public ResponseEntity<String> removeAnswerById(@PathVariable long id) {
        answerService.disableAnswer(id);
        return new ResponseEntity<>(
                "The answer with ID " + id + " is removed", HttpStatus.NO_CONTENT);

    }
}