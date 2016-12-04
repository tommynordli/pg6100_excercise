package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 03/11/2016.
 */

@NamedQueries({
        @NamedQuery(name = Subsubcategory.GET_SUBSUBCATEGORIES, query = "select c from Subsubcategory c"),
        @NamedQuery(name = Subsubcategory.GET_SUBSUBCATEGORY_BY_NAME, query = "select c from Subsubcategory c where c.name = :name")
})

@Entity
public class Subsubcategory {
    public static final String GET_SUBSUBCATEGORIES = "GET_SUBSUBCATEGORIES";
    public static final String GET_SUBSUBCATEGORY_BY_NAME = "GET_SUBSUBCATEGORY_BY_NAME"; //Parameter: name

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @OneToMany
    private List<Quiz> quizzes;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public void addQuiz(Quiz quiz){
        if (quizzes.isEmpty()){
            quizzes = new ArrayList<>();
        }

        quizzes.add(quiz);
    }
}
