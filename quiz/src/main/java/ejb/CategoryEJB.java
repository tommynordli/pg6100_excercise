package ejb;

import entity.Category;
import entity.Subcategory;
import entity.Subsubcategory;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Tommy on 03/11/2016.
 */

@Stateless
public class CategoryEJB {

    @PersistenceContext
    private EntityManager em;

    public CategoryEJB(){}

    public Category createCategory(@NotNull String name){
        if (getCategory(name) != null){
            return null;
        }

        Category category = new Category();
        category.setName(name);

        em.persist(category);
        return category;
    }

    public Subcategory createSubCategory(@NotNull String name, @NotNull String categoryName){
        Category category = getCategory(categoryName);

        if (category == null || getSubSubCategory(name) != null){
            return null;
        }

        Subcategory subcategory = new Subcategory();
        subcategory.setName(name);

        // updating subCategoriesList on category
        List<Subcategory> subCategories = category.getSubCategories();
        subCategories.add(subcategory);
        category.setSubCategories(subCategories);

        em.persist(subcategory);
        em.merge(category);

        return subcategory;
    }

    public Subsubcategory createSubSubCategory(@NotNull String name, @NotNull String subCategoryName){
        Subcategory subcategory = getSubCategory(subCategoryName);

        if (subcategory == null || getSubSubCategory(name) != null){
            return null;
        }

        Subsubcategory subsubcategory = new Subsubcategory();
        subsubcategory.setName(name);

        // updating cubSubCategories on subcategory
        List<Subsubcategory> subSubCategories = subcategory.getSubSubCategories();
        subSubCategories.add(subsubcategory);
        subcategory.setSubSubCategories(subSubCategories);

        em.persist(subsubcategory);
        em.merge(subcategory);

        return subsubcategory;
    }

    public Category getCategory(String name){
        List<Category> categories = em.createNamedQuery(Category.GET_CATEGORY_BY_NAME).setParameter("name", name).getResultList();
        if (!categories.isEmpty()){
            return categories.get(0);
        } else return null;
    }

    public Category getCategory(Long id){
        return em.find(Category.class, id);
    }

    public Category updateCategory(Long id, String name, List<Subcategory> subCategories) {
        Category category = getCategory(id);
        category.setName(name);
        category.setSubCategories(subCategories);
        em.merge(category);
        return category;
    }

    public Subcategory getSubCategory(String name){
        List<Subcategory> subCategories = em.createNamedQuery(Subcategory.GET_SUBCATEGORY_BY_NAME).setParameter("name", name).getResultList();
        if (!subCategories.isEmpty()){
            return subCategories.get(0);
        } else return null;
    }

    public Subcategory getSubCategory(Long id){
        return em.find(Subcategory.class, id);
    }

    public Subsubcategory getSubSubCategory(String name){
        List<Subsubcategory> subSubCategories = em.createNamedQuery(Subsubcategory.GET_SUBSUBCATEGORY_BY_NAME).setParameter("name", name).getResultList();
        if (!subSubCategories.isEmpty()){
            return subSubCategories.get(0);
        } else return null;
    }

    public List<Category> getCategories(){
        return em.createNamedQuery(Category.GET_CATEGORIES).getResultList();
    }

    public List<Subcategory> getSubCategories(){
        return em.createNamedQuery(Subcategory.GET_SUBCATEGORIES).getResultList();
    }

    public List<Subsubcategory> getSubSubCategories(){
        return em.createNamedQuery(Subsubcategory.GET_SUBSUBCATEGORIES).getResultList();
    }

    public void deleteCategory(Long id) {
        Category category = getCategory(id);
        for (Subcategory subcategory : category.getSubCategories()) {
            deleteSubCategory(id);
        }
        em.remove(em.find(Category.class, id));
    }

    public void deleteSubCategory(Long id) {
        Subcategory subcategory = getSubCategory(id);
        for (Subsubcategory subsubcategory : subcategory.getSubSubCategories()) {
            deleteSubSubCategory(id);
        }
        em.remove(em.find(Subcategory.class, id));
    }

    public void deleteSubSubCategory(Long id) {
        em.remove(em.find(Subsubcategory.class, id));
    }
}
