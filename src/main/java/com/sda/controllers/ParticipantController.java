package com.sda.controllers;

import com.sda.model.users.Participant;
import com.sda.services.ParticipantService;
import com.sda.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/participant")
public class ParticipantController {
    @Autowired
    private ParticipantService participantService;
    @Autowired
    private QuizService quizService;

    @GetMapping("/{quizId}")
    //returns a list of all participants associated with a single quiz
    public ResponseEntity<List<Participant>> getParticipantByQuizId(@PathVariable long quizId) {
        List<Participant> quizParticipants = quizService.getAllParticipantsByQuizId(quizId);
        return new ResponseEntity<List<Participant>>(quizParticipants, HttpStatus.OK);
    }

    @PostMapping("/create/{quizId}")
    // Creates and adds a participant to a quiz
    public ResponseEntity<Participant> createParticipantAndAddToQuiz(
            @RequestBody @Valid Participant participant, @PathVariable long quizId) {
        participantService.addParticipantToQuiz(quizId, participant);
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }
}
