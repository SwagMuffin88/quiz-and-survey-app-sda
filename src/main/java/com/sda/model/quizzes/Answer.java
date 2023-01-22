package com.sda.model.quizzes;

import lombok.*;

import javax.persistence.*;

@Entity @Data @NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @NotBlank(message = "Please write an answer")
    private String answerStatement;

//    @Column(columnDefinition="tinyint(1) default 1")
    private boolean isAvailable;

}
