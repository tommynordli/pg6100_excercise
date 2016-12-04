package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Tommy on 24/10/2016.
 */

@NamedQueries({
        @NamedQuery(name = Category.GET_CATEGORIES, query = "select c from Category c"),
        @NamedQuery(name = Category.GET_CATEGORY_BY_NAME, query = "select c from Category c where c.name = :name")
})

@Entity
public class Category {
    public static final String GET_CATEGORIES = "GET_CATEGORIES";
    public static final String GET_CATEGORY_BY_NAME = "GET_CATEGORY_BY_NAME"; //Parameter: name

    @Id @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @OneToMany
    private List<Subcategory> subCategories;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subcategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<Subcategory> subCategories) {
        this.subCategories = subCategories;
    }
}
