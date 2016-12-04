package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Tommy on 03/11/2016.
 */

@NamedQueries({
        @NamedQuery(name = Subcategory.GET_SUBCATEGORIES, query = "select c from Subcategory c"),
        @NamedQuery(name = Subcategory.GET_SUBCATEGORY_BY_NAME, query = "select c from Subcategory c where c.name = :name")
})

@Entity
public class Subcategory {
    public static final String GET_SUBCATEGORIES = "GET_SUBCATEGORIES";
    public static final String GET_SUBCATEGORY_BY_NAME = "GET_SUBCATEGORY_BY_NAME"; //Parameter: name

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String name;

    @OneToMany
    private List<Subsubcategory> subSubCategories;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subsubcategory> getSubSubCategories() {
        return subSubCategories;
    }

    public void setSubSubCategories(List<Subsubcategory> subSubCategories) {
        this.subSubCategories = subSubCategories;
    }
}
