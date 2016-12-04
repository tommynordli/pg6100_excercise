package dto;

import entity.Subcategory;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Tommy on 27/11/2016.
 */
public class CategoryDto {
    @ApiModelProperty("The category id")
    public String id;

    @ApiModelProperty("The category name")
    public String name;

    @ApiModelProperty("The category's subcategories")
    public List<Subcategory> subCategories;

    public CategoryDto(){}

    public CategoryDto(String id, String name, List<Subcategory> subCategories){
        this.id = id;
        this.name = name;
        this.subCategories = subCategories;
    }
}
