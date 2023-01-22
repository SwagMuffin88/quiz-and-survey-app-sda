package com.sda.services;

import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.quizzes.Answer;
import com.sda.model.quizzes.Question;
import com.sda.repositories.AnswerRepository;
import com.sda.repositories.AuthorRepository;
import com.sda.repositories.QuestionRepository;
import com.sda.repositories.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service @RequiredArgsConstructor @Transactional
public class AnswerService {
    private final AuthorRepository authorRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final QuestionService questionService;

    public Answer editAnswer(long answerId, Answer answer) {
        Answer answerToUpdate = findAnswerById(answerId);
        answerToUpdate.setAnswerStatement(answer.getAnswerStatement());
        answerToUpdate.setAvailable(true);
        saveAnswer(answerToUpdate);
        return answerToUpdate;
    }

    public Answer createAnswer(Answer answer) {
        Answer newAnswer = new Answer();
        newAnswer.setAnswerStatement(answer.getAnswerStatement());
        newAnswer.setAvailable(true);
        answerRepository.save(newAnswer);
        return newAnswer;
    }

    public void saveAnswer(Answer answer) {
        answerRepository.save(answer);
    }

    private Answer findAnswerById(long answerId) {
        return answerRepository.findById(answerId).orElseThrow(() ->
                new ResourceNotFoundException("Answer not found"));
    }
    public void addAnswerToQuestion(long questionId, Answer answer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        question.getAnswers().add(answer);
        questionService.saveQuestion(question);
    }

    public void disableAnswer(long answerId) {
        Answer answer = findAnswerById(answerId);
        answer.setAvailable(false);
        saveAnswer(answer);
    }
}
