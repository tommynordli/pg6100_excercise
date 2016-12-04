package ejb;

import embeddable.Question;
import entity.Quiz;
import entity.Subsubcategory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 03/11/2016.
 */

@Stateless
public class QuizEJB {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private CategoryEJB categoryEJB;

    public QuizEJB() {
    }

    public Quiz createQuiz(@NotNull String name, @NotNull List<Question> questions, @NotNull Subsubcategory subsubcategory) {
        if (subsubcategory == null) {
            return null;
        }

        Quiz quiz = new Quiz();
        quiz.setName(name);
        quiz.setQuestions(questions);
        quiz.setSubsubcategory(subsubcategory);

        subsubcategory.addQuiz(quiz);

        em.merge(subsubcategory);
        em.persist(quiz);
        return quiz;
    }

    public Quiz updateQuiz(@NotNull String name, @NotNull List<Question> questions, @NotNull Subsubcategory subsubcategory) {
        if (subsubcategory == null) {
            return null;
        }

        Quiz quiz = new Quiz();
        quiz.setName(name);
        quiz.setQuestions(questions);
        quiz.setSubsubcategory(subsubcategory);

        subsubcategory.addQuiz(quiz);

        em.merge(subsubcategory);
        em.merge(quiz);
        return quiz;
    }

    public Quiz getQuiz(@NotNull Long id) {
        return em.find(Quiz.class, id);
    }

    public void deleteQuiz(@NotNull Long id) {
        em.remove(em.find(Quiz.class, id));
    }

    public List<Quiz> getQuizzes() {
        return em.createNamedQuery(Quiz.GET_ALL_QUIZZES).getResultList();
    }

    /*
    public List<Quiz> getQuizzesInCategory(@NotNull String categoryName){
        List<Quiz> quizzes = new ArrayList<>();
        Category category = categoryEJB.getCategory(categoryName);

        if (category == null){
            return null;
        } else {
            for (Subcategory subCategory : category.getSubCategories()) {
                for (Subsubcategory subSubCategory : subCategory.getSubSubCategories()) {
                    quizzes.addAll(subSubCategory.getQuizzes());
                }
            }
            return quizzes;
        }
    }

    public List<Quiz> getQuizzesInSubCategory(@NotNull String subCategoryName){
        List<Quiz> quizzes = new ArrayList<>();
        Subcategory subCategory = categoryEJB.getSubCategory(subCategoryName);

        for (Subsubcategory subSubCategory : subCategory.getSubSubCategories()) {
            quizzes.addAll(subSubCategory.getQuizzes());
        }
        return quizzes;
    }
    */

    public List<Quiz> getQuizzesInSubSubCategory(@NotNull String subSubCategoryName) {
        List<Quiz> quizzes = new ArrayList<>();
        Subsubcategory subsubcategory = categoryEJB.getSubSubCategory(subSubCategoryName);

        quizzes.addAll(subsubcategory.getQuizzes());
        return quizzes;
    }

}
