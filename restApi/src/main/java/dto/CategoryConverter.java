package dto;

import entity.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Tommy on 27/11/2016.
 */
public class CategoryConverter {

    private CategoryConverter(){}

    public static CategoryDto transform(Category entity) {
        Objects.requireNonNull(entity);
        CategoryDto dto = new CategoryDto();
        dto.id = String.valueOf(entity.getId());
        dto.name = entity.getName();
        dto.subCategories = entity.getSubCategories();

        return dto;
    }

    public static List<CategoryDto> transform(List<Category> entities) {
        Objects.requireNonNull(entities);

        List<CategoryDto> categories = new ArrayList<>();

        for (Category entity : entities) {
            categories.add(transform(entity));
        }

        return categories;
    }
}
