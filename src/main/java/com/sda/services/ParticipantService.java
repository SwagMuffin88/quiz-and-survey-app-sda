package com.sda.services;

import com.sda.exceptions.ResourceNotFoundException;
import com.sda.model.quizzes.Quiz;
import com.sda.model.users.Participant;
import com.sda.repositories.AuthorRepository;
import com.sda.repositories.ParticipantRepository;
import com.sda.repositories.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class ParticipantService {
    private final QuizRepository quizRepository;
    private final ParticipantRepository participantRepository;
    private final QuizService quizService;


    public Participant createParticipant(Participant participant) {
        return participantRepository.save(participant);
    }

    public Participant addParticipantToQuiz(long quizId, Participant participant) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() ->
                new ResourceNotFoundException("Quiz not found"));
        Participant newParticipant = createParticipant(participant);
        quiz.getParticipantList().add(newParticipant);
        quizService.saveQuiz(quiz);
        return newParticipant;
    }

}
