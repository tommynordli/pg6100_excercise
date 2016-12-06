import dto.QuizDto;
import ejb.CategoryEJB;
import embeddable.Question;
import entity.Category;
import entity.Subcategory;
import entity.Subsubcategory;
import io.restassured.http.ContentType;
import org.junit.Test;

import javax.ejb.EJB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;

/**
 * Created by Tommy on 04/12/2016.
 */
public class QuizzesRestApiTestIT extends QuizzesRestTestBase{

    @EJB
    CategoryEJB categoryEJB;

    @Test
    public void testCleanDB(){
        get().then().statusCode(200).body("size()", is(0));
    }

    @Test
    public void testCreateAndGet(){
        QuizDto dto = new QuizDto();
        Category category = categoryEJB.createCategory("Movies");
        Subcategory subcategory = categoryEJB.createSubCategory("Fiction", category.getName());
        Subsubcategory subsubcategory = categoryEJB.createSubSubCategory("Harry Potter", subcategory.getName());
        List<Question> questions = new ArrayList<>();
        questions.add(new Question("Who's the main character?", Arrays.asList("Harry", "Hermoine", "Ron", "Ginny"), "Harry"));
        questions.add(new Question("Is Snape good?", Arrays.asList("Yes", "No", "Idk", "Maybe"), "Yes"));

        dto.name = "Harry Potter quiz";
        dto.subsubcategory = subsubcategory;
        dto.questions = questions;

        get().then().statusCode(200).body("size()", is(0));

        String id = given().contentType(ContentType.JSON)
                .body(dto)
                .post()
                .then()
                .statusCode(200)
                .extract()
                .asString();

        get().then().statusCode(200).body("size()", is(1));

        given().pathParam("id", id)
                .get("/id/{id}")
                .then()
                .statusCode(200)
                .body("id", is(id))
                .body("name", is("Harry Potter quiz"))
                .body("questions", is(questions));
    }
}