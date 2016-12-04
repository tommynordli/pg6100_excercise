package embeddable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 03/11/2016.
 */

@Entity
public class Question {

    @Id @GeneratedValue
    private Long id;

    private String question;

    @ElementCollection @OrderColumn
    private List<String> answers = new ArrayList<>();

    private String correctAnswer;

    public Question() {
    }

    public Question(String question, List<String> answers, String correctAnswer) {
        this.question = question;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    public Long getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
