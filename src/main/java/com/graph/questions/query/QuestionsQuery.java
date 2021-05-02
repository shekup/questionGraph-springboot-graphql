package com.graph.questions.query;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.graph.questions.domain.Question;
import com.graph.questions.domain.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuestionsQuery implements GraphQLQueryResolver {

    @Autowired
    QuestionService questionService;

    public Question rootQuestion(){
        return questionService.getRootQuestion();
    }

    public Question getQuestion(int id){
        return questionService.searchByQuestionId(id);
    }

    public Question getNextQuestion(String answerToPreviousQuestion, int previousQuestionId){
        return questionService.retrieveNextQuestion(answerToPreviousQuestion, previousQuestionId);
    }

}
