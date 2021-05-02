package com.graph.questions.domain;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Set;

@Component
public class QuestionAggregate {

    MutableValueGraph<Question, String> questionGraph;

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
        questionGraph.addNode(income);

        // Add employment question
        Question employment = new Question();
        employment.setQuestion("Currently employed");
        employment.setHelpText("Your current employment");
        employment.setReferenceAnswers(referenceAnswers);
        questionGraph.addNode(employment);

        // Add link between two Income and Employment
        questionGraph.putEdgeValue(income, employment, "Yes");

        Question company = new Question();
        company.setQuestion("Current company");
        company.setHelpText("Your current company");
        company.setQuestionType("leaf");
        questionGraph.putEdgeValue(employment, company, "Yes");
    }

    /**
     *  This method needs performance improvement
      */
    Question searchByQuestionId(int id){
        Set<Question> questions = questionGraph.nodes();
        return questions.stream().filter(question -> id == question.getId()).findFirst().orElse(null);
    }

    Question retrieveNextQuestion(String answerToPreviousQuestion, int previousQuestionId){
        Question previousQuestion = searchByQuestionId(previousQuestionId);
        Set<Question> questions = questionGraph.adjacentNodes(searchByQuestionId(previousQuestionId));
        for(Question question: questions){
            if(questionGraph.edgeValue(previousQuestion, question).equals(answerToPreviousQuestion)){
                return question;
            }
        }
        return null;
    }

}
