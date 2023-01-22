package com.sda.model.quizzes;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity @Data @NoArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotBlank(message = "Please write a question")
    private String questionStatement;

    @OneToOne(cascade = CascadeType.MERGE)
    private Answer correctAnswer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Answer> answers;

//    @Column(columnDefinition="tinyint(1) default 1")
    private boolean isAvailable;

}
