package api.categories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import dto.CategoryConverter;
import dto.CategoryDto;
import ejb.CategoryEJB;
import ejb.QuizEJB;
import entity.Category;
import entity.Subcategory;
import entity.Subsubcategory;
import io.swagger.annotations.ApiParam;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tommy on 27/11/2016.
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class CategoriesRestImpl implements CategoriesRestApi {
    @EJB
    private CategoryEJB categoryEJB;
    @EJB
    private QuizEJB quizEJB;

    @Override
    public List<CategoryDto> get() {
        return CategoryConverter.transform(categoryEJB.getCategories());
    }

    @Override
    public Long createCategory(@ApiParam("Category name. Id is auto-generated") CategoryDto dto) {
        if (dto.id != null) {
            throw new WebApplicationException("Cannot specify id for a newly generated category", 400);
        }

        Long id;
        try {
            id = categoryEJB.createCategory(dto.name).getId();
        } catch (Exception e) {
            throw new WebApplicationException("Cannot create category: " + e.getMessage(), 400);
        }

        return id;
    }

    @Override
    public CategoryDto getById(@ApiParam(ID_PARAM) Long id) {
        return CategoryConverter.transform(categoryEJB.getCategory(id));
    }

    @Override
    public void update(@ApiParam("id") Long pathId, @ApiParam("The category that will replace the old one. Cannot change the id") CategoryDto dto) {
        Long id;

        try {
            id = Long.parseLong(dto.id);
        } catch (Exception e) {
            throw new WebApplicationException("Invalid id: " + dto.id, 400);
        }

        if (id != pathId) {
            // 409 = conflict
            throw new WebApplicationException("Not allowed to change the if of the resource", 409);
        }

        if (categoryEJB.getCategory(id) == null) {
            throw new WebApplicationException("Can't find category with id: " + id, 404);
        }

        try {
            categoryEJB.updateCategory(id, dto.name, dto.subCategories);
        } catch (Exception e) {
            throw wrapException(e);
        }
    }

    @Override
    public void mergePatch(@ApiParam("The id of the category") Long id, @ApiParam("The partial patch") String jsonPatch) {
        CategoryDto dto = CategoryConverter.transform(categoryEJB.getCategory(id));
        if (dto == null) {
            throw new WebApplicationException("Can't find category with id " + id, 404);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readValue(jsonPatch, JsonNode.class);
        } catch (Exception e) {
            throw new WebApplicationException("Invalid JSON data as input: " + e.getMessage(), 400);
        }

        if (jsonNode.has("id")) {
            throw new WebApplicationException("Can't modify id " + id + " to id " + jsonNode.get("id"), 409);
        }

        String newName = dto.name;
        List<Subcategory> newSubCategories = dto.subCategories;

        if (jsonNode.has("name")) {
            JsonNode nameNode = jsonNode.get("name");
            if (nameNode.isNull()) {
                newName = null;
            } else if (nameNode.isTextual()) {
                newName = nameNode.asText();
            } else {
                throw new WebApplicationException("Invalid JSON. Non-string name", 400);
            }
        }

        if (jsonNode.has("subcategories")) {
            JsonNode subCategoriesNode = jsonNode.get("subcategories");
            if (subCategoriesNode.isNull()) {
                newSubCategories = null;
            } else if (subCategoriesNode.isArray()) {
                try {
                    newSubCategories = objectMapper.treeToValue(subCategoriesNode, dto.subCategories.getClass());
                } catch (Exception e) {
                    throw new WebApplicationException("Invalid JSON data as input: " + e.getMessage(), 400);
                }
            } else {
                throw new WebApplicationException("Invalid JSON format for subcategories", 400);
            }
        }

        dto.name = newName;
        dto.subCategories = newSubCategories;
    }

    @Override
    public void delete(@ApiParam(ID_PARAM) Long id) {
        categoryEJB.deleteCategory(id);
    }

    @Override
    public List<Category> getCategoriesWithQuizzes() {
        List<Category> categories = new ArrayList<>();

        for (Category category : categories) {
            outerloop:
            for (Subcategory subcategory : category.getSubCategories()) {
                for (Subsubcategory subsubcategory : subcategory.getSubSubCategories()) {
                    if (!quizEJB.getQuizzesInSubSubCategory(subsubcategory.getName()).isEmpty()) {
                        categories.add(category);
                        break outerloop;
                    }
                }
            }
        }

        return categories;
    }

    @Override
    public List<Subcategory> getSubCategoriesWithQuizzes() {
        List<Subcategory> subCategories = new ArrayList<>();

        for (Subcategory subcategory : subCategories) {
            outerloop:
            for (Subsubcategory subsubcategory : subcategory.getSubSubCategories()) {
                if (!quizEJB.getQuizzesInSubSubCategory(subsubcategory.getName()).isEmpty()) {
                    subCategories.add(subcategory);
                    break outerloop;
                }
            }
        }

        return subCategories;
    }

    @Override
    public List<Subcategory> getSubCategoriesOfCategory(@ApiParam(ID_PARAM) Long id) {
        return categoryEJB.getCategory(id).getSubCategories();
    }

    private WebApplicationException wrapException(Exception e) throws WebApplicationException {

        /*
            Errors:
            4xx: the user has done something wrong, eg asking for something that does not exist (404)
            5xx: internal server error (eg, could be a bug in the code)
         */

        Throwable cause = Throwables.getRootCause(e);
        if (cause instanceof ConstraintViolationException) {
            return new WebApplicationException("Invalid constraints on input: " + cause.getMessage(), 400);
        } else {
            return new WebApplicationException("Internal error", 500);
        }
    }
}
