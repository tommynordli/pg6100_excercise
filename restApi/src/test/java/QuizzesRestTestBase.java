import dto.QuizDto;
import io.restassured.http.ContentType;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import utils.JBossUtil;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.Is.is;

/**
 * Created by Tommy on 04/12/2016.
 */
public class QuizzesRestTestBase {

    @BeforeClass
    public static void initClass(){
        JBossUtil.waitForJBoss(10);

        baseURI = "http://localhost";
        port = 8080;
        basePath = "/restApi/api/quizzes";
        enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Before @After
    public void clean(){
        List<QuizDto> dtoList = Arrays.asList(
                given().
                accept(ContentType.JSON).
                get().
                then().
                statusCode(200).
                extract().
                as(QuizDto.class));

        dtoList.stream().forEach(dto ->
                given().pathParam("id", dto.id).delete("/id/{id}").then().statusCode(204));

        get().then().statusCode(200).body("size()", is(0));
    }
}
