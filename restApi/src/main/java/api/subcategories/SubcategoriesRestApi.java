package api.subcategories;

import entity.Subcategory;
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

@Api(value = "/subcategories", description = "Handling creation and retrieving of subcategories")
@Path("/subcategories")
@Produces(MediaType.APPLICATION_JSON)
public interface SubcategoriesRestApi {

    String ID_PARAM = "The id of the subcategory";

    // /subcategories/parent/{id}
    @ApiOperation("GET all subcategories with the given parent specified by id")
    @GET
    @Path("/parent/{id}")
    List<Subcategory> getSubcategoriesWithParent(
            @ApiParam("id of parent (category)")
            @PathParam("id")
                    Long id);

    // /subcategories/id/{id}/subsubcategories
    @ApiParam("GET all subsubcategories of the subcategory specified by id")
    @GET
    @Path("id/{id}/subsubcategories")
    List<Subsubcategory> getSubsubcategoriesOfSubcategory(
            @ApiParam(ID_PARAM)
                    Long id
    );
}
