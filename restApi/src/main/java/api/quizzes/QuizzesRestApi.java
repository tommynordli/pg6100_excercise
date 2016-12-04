package api.quizzes;

import dto.QuizDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.jaxrs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Tommy on 25/11/2016.
 */

@Api(value = "/quizzes", description = "Handling of creating and retrieving quizzes")
@Path("/quizzes")
@Produces(MediaType.APPLICATION_JSON)
public interface QuizzesRestApi {

    String ID_PARAM = "The id of the quizzes ";

    // /quizzes GET
    @ApiOperation("Get all the quizzes")
    @GET
    List<QuizDto> get();

    // /quizzes POST
    @ApiOperation("Post a quiz")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponse(code = 200, message = "The id of newly created quiz")
    Long createQuiz(
            @ApiParam("Quiz name, questions (with answer) and subsubcategory. Id is auto-generated")
                    QuizDto dto);

    // /quizzes/id/{id} GET
    @ApiOperation("Get a quiz with specified id")
    @GET
    @Path("/id/{id}")
    QuizDto getById(
            @ApiParam(ID_PARAM)
            @PathParam("id")
                    Long id);

    // /quizzes/id/{id} PUT
    @ApiOperation("Update a quiz with specified id")
    @PUT
    @Path("/id/{id}")
    QuizDto update(
            @ApiParam(ID_PARAM)
            @PathParam("id")
                    Long id,
            @ApiParam("The quiz that will replace the old one. Cannot change id")
                    QuizDto dto);

    // /quizzes/id/{id} PATCH
    @ApiOperation("Modify the quiz using JSON Merge Patch")
    @PATCH
    @Path("/id/{id}")
    @Consumes("application/merge-patch+json")
    void mergePatch(@ApiParam("The id of the quiz")
                    @PathParam("id")
                            Long id,
                    @ApiParam("The partial patch")
                            String jsonPatch);

    // /quizzes/id/{id} DELETE
    @ApiOperation("Delete a quiz with specified id")
    @DELETE
    @Path("/id/{id}")
    void delete(
            @ApiParam(ID_PARAM)
            @PathParam("id")
                    Long id);
}
