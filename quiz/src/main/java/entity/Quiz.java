package entity;

import embeddable.Question;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Tommy on 03/11/2016.
 */

@NamedQueries(
        @NamedQuery(name = Quiz.GET_ALL_QUIZZES, query = "select q from Quiz q")
)

@Entity
public class Quiz {

    public static final String GET_ALL_QUIZZES = "GET_ALL_QUIZZES";
    @Id @GeneratedValue
    private Long id;

    private String name;

    @ManyToOne
    private Subsubcategory subsubcategory;

    @OneToMany
    private List<Question> questions;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subsubcategory getSubsubcategory() {
        return subsubcategory;
    }

    public void setSubsubcategory(Subsubcategory subsubcategory) {
        this.subsubcategory = subsubcategory;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}

