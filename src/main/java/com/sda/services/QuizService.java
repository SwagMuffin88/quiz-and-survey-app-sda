package com.sda.services;

import com.sda.model.quizzes.Answer;
import com.sda.model.quizzes.Question;
import com.sda.model.quizzes.Quiz;
import com.sda.model.users.Author;
import com.sda.repositories.AnswerRepository;
import com.sda.repositories.AuthorRepository;
import com.sda.repositories.QuestionRepository;
import com.sda.repositories.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class QuizService {
    private final AuthorRepository authorRepository;
    private final QuizRepository quizRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    public Quiz createQuiz( Quiz quiz) {
        List<Question> quizQuestions = new ArrayList<>();
        for (Question question : quiz.getQuestions()) {
            List<Answer> answersList = new ArrayList<>();
            Question newQuestion = new Question();
            newQuestion.setQuestionStatement(question.getQuestionStatement());
            for (Answer a : question.getAnswers()) {
                answerRepository.save(a);
                answersList.add(a);
            }
            newQuestion.setAnswers(answersList);
            newQuestion.setCorrectAnswer(question.getAnswers().get(0).getAnswerStatement());
            questionRepository.save(newQuestion);
            quizQuestions.add(newQuestion);
        }
        Quiz q = new Quiz(quiz.getQuizTitle(), quiz.getQuizDescription(), quizQuestions, quiz.isPrivateStatus());
        return q;
    }

    public Quiz saveQuiz (Quiz quiz){return quizRepository.save(quiz);}

    public void addQuizToAuthor(String username, String quizTitle) {
        Author author = authorRepository.findByUsername(username);
        Quiz quiz = quizRepository.findByQuizTitle(quizTitle);
        if (quiz != null) author.getQuizzes().add(quiz);
    }

    public void deleteQuiz(int quizId) {
        quizRepository.deleteById(quizId);
    }
}
