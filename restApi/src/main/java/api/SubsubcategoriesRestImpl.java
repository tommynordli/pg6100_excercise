package api;

import ejb.CategoryEJB;
import entity.Subsubcategory;
import io.swagger.annotations.ApiParam;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;

/**
 * Created by Tommy on 01/12/2016.
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class SubsubcategoriesRestImpl implements SubsubcategoriesRestApi {

    @EJB
    private CategoryEJB categoryEJB;

    @Override
    public List<Subsubcategory> getSubsubcategoriesOfSubcategory(@ApiParam(ID_PARAM) Long id) {
        return categoryEJB.getSubCategory(id).getSubSubCategories();
    }
}
