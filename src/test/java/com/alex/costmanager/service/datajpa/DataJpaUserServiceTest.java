package com.alex.costmanager.service.datajpa;

import com.alex.costmanager.CostTestData;
import com.alex.costmanager.service.AbstractJpaUserServiceTest;
import com.alex.model.User;
import com.alex.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static com.alex.Profiles.DATAJPA;
import static com.alex.costmanager.UserTestData.*;


@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractJpaUserServiceTest {

    @Test
    public void getWithCosts()  {
        User admin = service.getWithCosts(ADMIN_ID);
        assertMatch(admin, ADMIN);
        CostTestData.assertMatch(admin.getCosts(), CostTestData.ADMIN_COST2, CostTestData.ADMIN_COST1);
    }

    @Test(expected = NotFoundException.class)
    public void getWithCostsNotFound() {
        service.getWithCosts(1);
    }
    
}