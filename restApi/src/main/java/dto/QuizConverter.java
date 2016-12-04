package dto;

import entity.Quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Tommy on 26/11/2016.
 */
public class QuizConverter {

    private QuizConverter(){}

    public static QuizDto transform(Quiz entity){
        Objects.requireNonNull(entity);
        QuizDto dto = new QuizDto();
        dto.id = String.valueOf(entity.getId());
        dto.name = entity.getName();
        dto.questions = entity.getQuestions();

        return dto;
    }

    public static List<QuizDto> transform(List<Quiz> entities){
        Objects.requireNonNull(entities);

        List<QuizDto> quizzes = new ArrayList<>();

        for (Quiz entity : entities) {
            quizzes.add(transform(entity));
        }

        return quizzes;
    }
}
