# Getting Started

## Purpose
The problem is server side maintains few questions that are asked to end user. 
</br> 
</br> One option is to maintain list of questions in an ArrayList and expose few REST endpoints
</br> GET /questions : returns all questions
</br> GET /questions/{id} : returns a particular question
</br> But, questions are hierarchical, that is, answer of one question determines the next question.  Such as, if only end user has Income then ask employment details.  Only if user is employed then ask company details.  
</br> Alternate design is using graphs and graphql. 

## Design
The questions are maintained in a graph (using Google Guava Graph) in which each node is a Question.  Each Question can have few answers, such as, Income can have two answers "Yes" and "No".  If answer is "Yes" then ask next Question, which asks more details on Income.  If answer to "Income" is "No" then dont ask any further questions. 
</br> The next step is exposing queries (using GraphQl) that can be used by client application to traverse the Question graph, such as, give me first question, give me next question, give me previous question, and so on

### Sample requests
</br>query { 
</br>  rootQuestion
</br>  {
</br>    id,
</br>    question,
</br>    helpText,
</br>    referenceAnswers
</br>  }
</br>}

</br>query { 
</br>  nextQuestion(answerToPreviousQuestion: "Yes", previousQuestionId: 0)
</br>  {
</br>    id,
</br>    question,
</br>    helpText,
</br>    referenceAnswers
</br>  }
</br>}

## Reference Documentation
For further reference, please consider the following sections:

* [GraphQL Spring boot](https://github.com/graphql-java-kickstart/graphql-spring-boot)
* [Graphs Explained](https://github.com/google/guava/wiki/GraphsExplained)

