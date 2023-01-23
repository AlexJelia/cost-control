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
        User user = service.getWithCosts(USER_ID);
        assertMatch(user, USER);
        CostTestData.assertMatch(user.getCosts(), CostTestData.COSTS);
    }

    @Test(expected = NotFoundException.class)
    public void getWithCostsNotFound() {
        service.getWithCosts(1);
    }
    
}