package ejb;

import entity.Category;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * Created by Tommy on 14/11/2016.
 */
@RunWith(Arquillian.class)
public class CategoryEJBTest {

    private EntityManagerFactory emFactory;
    private EntityManager em;

    @EJB
    private CategoryEJB categoryEJB;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void createCategory() throws Exception{
        Category category = categoryEJB.createCategory("Science");
        Assert.assertNotNull(category);
    }

    @Test
    public void createExistingCategory() throws Exception{

    }

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(CategoryEJB.class)
                .addPackages(true, "ejb", "embeddable", "entity")
                .addAsResource("META-INF/persistence.xml");
    }
}