package com.graph.questions.domain;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class QuestionService {

    @Autowired
    QuestionAggregate questionAggregate;

    /**
     *  This method needs performance improvement
     */
    public Question searchByQuestionId(int id){
        Set<Question> questions = questionAggregate.questions();
        return questions.stream().filter(question -> id == question.getId()).findFirst().orElse(null);
    }

    public Question retrieveNextQuestion(String answerToPreviousQuestion, int previousQuestionId){
        Question question = searchByQuestionId(previousQuestionId);
        return questionAggregate.retrieveNextQuestion(answerToPreviousQuestion, question);
    }

    public Question getRootQuestion(){
        Set<Question> questions = questionAggregate.questions();
        return questions.stream().filter(question -> "root".equals(question.getQuestionType())).findFirst().orElse(null);
    }

}
