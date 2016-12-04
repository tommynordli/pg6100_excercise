package api;

import dto.CategoryDto;
import entity.Category;
import entity.Subcategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.jaxrs.PATCH;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Tommy on 27/11/2016.
 */

@Api(value = "/categories", description = "Handling creation and retrieving  of categories")
@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
public interface CategoriesRestApi {

    String ID_PARAM = "The id of the category";

    // /categories GET
    @ApiOperation("Get all the categories")
    @GET
    List<CategoryDto> get();

    // /categories POST
    @ApiOperation("Post a category")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiResponse(code = 200, message = "The id of newly created category")
    Long createCategory(
            @ApiParam("Category name. Id is auto-generated")
                    CategoryDto dto);

    // /categories/id/{id} GET
    @ApiOperation("Get a quiz with specified id")
    @GET
    @Path("/id/{id}")
    CategoryDto getById(
            @ApiParam(ID_PARAM)
            @PathParam("id")
                    Long id);

    // /categories/id/{id} PUT
    @ApiOperation("Get a category with specified id")
    @PUT
    @Path("id/{id}")
    void update(
            @ApiParam("id")
            @PathParam("id")
                    Long id,
            @ApiParam("The category that will replace the old one. Cannot change the id")
                    CategoryDto dto);

    // /categories/id/{id} PATCH
    @ApiOperation("Modify the category using JSON Merge Patch")
    @PATCH
    @Path("/id/{id}")
    @Consumes("application/merge-patch+json")
    void mergePatch(@ApiParam(ID_PARAM)
                    @PathParam("id")
                            Long id,
                    @ApiParam("The partial patch")
                            String jsonPatch);

    // /categories/id/{id} DELETE
    @ApiOperation("Delete a quiz with specified id")
    @DELETE
    @Path("/id/{id}")
    void delete(
            @ApiParam(ID_PARAM)
            @PathParam("id")
                    Long id
    );

    // /categories/withQuizzes GET
    @ApiOperation("Get all categories that contains quizzes")
    @GET
    @Path("/withQuizzes")
    List<Category> getCategoriesWithQuizzes();

    // /categories/withQuizzes/subsubcategories GET
    @ApiOperation("Get all subsubcategories that contains quizzes")
    @GET
    @Path("/withQuizzes/subsubcategories")
    List<Subcategory> getSubCategoriesWithQuizzes();

    // /categories/id/{id}/subcategories GET
    @ApiOperation("Get all subcategories of the category specified by id")
    @GET
    @Path("/id/{id}/subcategories")
    List<Subcategory> getSubCategoriesOfCategory(
            @ApiParam(ID_PARAM)
            @PathParam("id")
                    Long id
    );
}
