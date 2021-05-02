package com.graph.questions.domain;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class QuestionAggregate {

    MutableValueGraph<Question, String> questionGraph;
    AtomicInteger id = new AtomicInteger(0);

    @PostConstruct
    void buildQuestionGraph(){
        questionGraph = ValueGraphBuilder.directed().build();

        ArrayList<String> referenceAnswers = new ArrayList<>();
        referenceAnswers.add("Yes");
        referenceAnswers.add("No");

        // Add Income questions
        Question income = new Question();
        income.setQuestion("Income");
        income.setHelpText("Answer income related details");
        income.setReferenceAnswers(referenceAnswers);
        income.setQuestionType("root");
        income.setId(id.getAndIncrement());
        questionGraph.addNode(income);

        // Add employment question
        Question employment = new Question();
        employment.setQuestion("Currently employed");
        employment.setHelpText("Your current employment");
        employment.setReferenceAnswers(referenceAnswers);
        employment.setId(id.getAndIncrement());
        questionGraph.addNode(employment);

        // Add link between two Income and Employment
        questionGraph.putEdgeValue(income, employment, "Yes");

        Question company = new Question();
        company.setQuestion("Current company");
        company.setHelpText("Your current company");
        company.setQuestionType("leaf");
        company.setId(id.getAndIncrement());
        questionGraph.putEdgeValue(employment, company, "Yes");
    }

    Set<Question> questions(){
        return questionGraph.nodes();
    }

    Question retrieveNextQuestion(String answerToPreviousQuestion, Question previousQuestion){
        Set<Question> questions = questionGraph.adjacentNodes(previousQuestion);
        for(Question question: questions){
            String value = questionGraph.edgeValue(previousQuestion, question).get();
            if(value.equals(answerToPreviousQuestion))
                return question;
        }
        return null;
    }

}
