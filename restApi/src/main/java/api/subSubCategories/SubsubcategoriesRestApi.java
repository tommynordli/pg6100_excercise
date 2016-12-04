package api.subSubCategories;

import entity.Subsubcategory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Tommy on 01/12/2016.
 */

@Api(value = "/subsubcategories", description = "Handling creation and retrieving of subsubcategories")
@Path("/subsubcategories")
@Produces(MediaType.APPLICATION_JSON)
public interface SubsubcategoriesRestApi {

    String ID_PARAM = "The id of the subsubcategory";

    // /subsubcategories/parent/{id}
    @ApiOperation("GET all subsubcategories with the given subcategory parent specified by id")
    @GET
    @Path("/subsubcategories/parent/{id}")
    List<Subsubcategory> getSubsubcategoriesOfSubcategory(
            @ApiParam("Id of the subcategory")
            @PathParam("id")
                    Long id
    );
}
