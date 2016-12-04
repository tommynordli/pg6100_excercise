package api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import dto.QuizConverter;
import dto.QuizDto;
import ejb.QuizEJB;
import embeddable.Question;
import entity.Subsubcategory;
import io.swagger.annotations.ApiParam;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import java.util.List;

/**
 * Created by Tommy on 26/11/2016.
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class QuizzesRestImpl implements QuizzesRestApi {
    @EJB
    private QuizEJB quizEJB;

    @Override
    public List<QuizDto> get() {
        return QuizConverter.transform(quizEJB.getQuizzes());
    }

    @Override
    public Long createQuiz(@ApiParam("Quiz name, questions (with answer) and subsubcategory. id is auto-generated") QuizDto dto) {
        if (dto.id != null){
            throw new WebApplicationException("Cannot specify id for a newly generated quiz", 400);
        }

        Long id;
        try {
            id = quizEJB.createQuiz(dto.name, dto.questions, dto.subsubcategory).getId();
        } catch (Exception e){
            throw wrapException(e);
        }

        return id;
    }

    @Override
    public QuizDto getById(@ApiParam(ID_PARAM) Long id) {
        return QuizConverter.transform(quizEJB.getQuiz(id));
    }

    @Override
    public QuizDto update(@ApiParam(ID_PARAM) Long id, @ApiParam("The quiz that will replace the old one. Cannot change id") QuizDto dto) {
        return null;
    }

    @Override
    public void mergePatch(@ApiParam("The id of the quiz") Long id, @ApiParam("The partial patch") String jsonPatch) {
        QuizDto dto = QuizConverter.transform(quizEJB.getQuiz(id));
        if (dto == null){
            throw new WebApplicationException("Cannot find quiz with id " + id, 404);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readValue(jsonPatch, JsonNode.class);
        } catch (Exception e){
            throw new WebApplicationException("Invalid JSON data as input: " + e.getMessage(), 400);
        }

        if (jsonNode.has("id")) {
            throw new WebApplicationException("Cannot modify the quiz id from " + id + " to " + jsonNode.get("id"), 409);
        }

        String newName = dto.name;
        Subsubcategory newSubsubcategory = dto.subsubcategory;
        List<Question> newQuestions = dto.questions;

        if (jsonNode.has("name")){
            JsonNode nameNode = jsonNode.get("name");
            if (nameNode.isNull()){
                newName = null;
            } else if (nameNode.isTextual()) {
                newName = nameNode.asText();
            } else {
                throw new WebApplicationException("Invalid JSON. Non-string name", 400);
            }
        }

        if (jsonNode.has("subsubcategory")){
            JsonNode subSubCategoryNode = jsonNode.get("subsubcategory");
            if (subSubCategoryNode.isNull()){
                newSubsubcategory = null;
            } else if (subSubCategoryNode.isPojo()){
                try {
                    newSubsubcategory = objectMapper.treeToValue(subSubCategoryNode, Subsubcategory.class);
                }catch (Exception e){
                    throw new WebApplicationException("Invalid JSON data as input: " + e.getMessage(), 400);
                }
            } else {
                throw new WebApplicationException("Invalid JSON format for subsubcategory", 400);
            }
        }

        if (jsonNode.has("questions")){
            JsonNode questionsNode = jsonNode.get("questions");
            if (questionsNode.isNull()){
                newQuestions = null;
            } else if (questionsNode.isArray()){
                try {
                    newQuestions = objectMapper.treeToValue(questionsNode, dto.questions.getClass());
                }catch (Exception e){
                    throw new WebApplicationException("Invalid JSON data as input: " + e.getMessage(), 400);
                }
            }else {
                throw new WebApplicationException("Invalid JSON format for questions", 400);
            }
        }

        dto.name = newName;
        dto.subsubcategory = newSubsubcategory;
        dto.questions = newQuestions;
    }


    @Override
    public void delete(@ApiParam(ID_PARAM) Long id) {
        quizEJB.deleteQuiz(id);
    }


    private WebApplicationException wrapException(Exception e) throws WebApplicationException{

        /*
            Errors:
            4xx: the user has done something wrong, eg asking for something that does not exist (404)
            5xx: internal server error (eg, could be a bug in the code)
         */

        Throwable cause = Throwables.getRootCause(e);
        if(cause instanceof ConstraintViolationException){
            return new WebApplicationException("Invalid constraints on input: "+cause.getMessage(), 400);
        } else {
            return new WebApplicationException("Internal error", 500);
        }
    }
}
