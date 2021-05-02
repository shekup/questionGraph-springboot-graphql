package com.graph.questions.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;

@Data
public class Question {

    @Id
    @GeneratedValue
    int id;

    String question;

    String helpText;

    ArrayList<String> referenceAnswers;

    String questionType;

    public String toString(){
        return question;
    }

    public boolean equals (Question anotherQuestion){
        if (this.id == anotherQuestion.id)
            return true;
        return false;
    }
}
