
package com.blazebit.persistence.bugs;

import com.blazebit.persistence.CriteriaBuilder;
import com.blazebit.persistence.bugs.entity.Person;
import com.blazebit.persistence.criteria.BlazeCriteriaBuilder;
import com.blazebit.persistence.criteria.BlazeCriteriaQuery;
import com.blazebit.persistence.criteria.impl.BlazeCriteria;
import com.blazebit.persistence.testsuite.AbstractCoreTest;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.EntityViewSetting;
import com.blazebit.persistence.view.EntityViews;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.spi.EntityViewConfiguration;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class IssueTest extends AbstractCoreTest {

    @Override
    protected Class<?>[] getEntityClasses() {
        return new Class<?>[] {
            Person.class
        };
    }
    
    /***************************************************************************
     * Test normal criteria query like this
     **************************************************************************/
    
    @Test
    public void testBasicQuery() {
        CriteriaBuilder<Person> cb = cbf.create(em, Person.class);
        
        List<Person> result = cb.getResultList();
        assertEquals(0, result.size());
    }
    
    /***************************************************************************
     * Test entity views like this
     **************************************************************************/
    
    @Test
    public void testBasicEntityView() {
        EntityViewManager evm = createEntityViewManager(PersonView.class);
        
        CriteriaBuilder<Person> cb = cbf.create(em, Person.class);
        
        // EntityView setting
        EntityViewSetting<PersonView, CriteriaBuilder<PersonView>> setting;
        setting = EntityViewSetting.create(PersonView.class);
        
        // Apply EntityView and get results
        List<PersonView> result = evm.applySetting(setting, cb).getResultList();
        
        // Assertions
        assertEquals(0, result.size());
    }
    
    @EntityView(Person.class)
    static interface PersonView {
        @IdMapping("id")
        public Long getId();
    }
    
    /***************************************************************************
     * Test JPA criteria query like this
     **************************************************************************/
    
    @Test
    public void testCriteriaQuery() {
        BlazeCriteriaBuilder criteriaBuilder = BlazeCriteria.get(em, cbf);
        BlazeCriteriaQuery<Person> query = criteriaBuilder.createQuery(Person.class);
        
        List<Person> result = query.getResultList();
        assertEquals(0, result.size());
    }
    
    /***************************************************************************
     * Here come utility methods
     **************************************************************************/
    
    private EntityViewManager createEntityViewManager(Class<?>... entityViewClasses) {
        // Configure and create the EntityViewManager
        EntityViewConfiguration cfg = EntityViews.createDefaultConfiguration();
        for (Class<?> clazz : entityViewClasses) {
            cfg.addEntityView(clazz);
        }
        return cfg.createEntityViewManager(cbf, em.getEntityManagerFactory());
    }
}
