package com.alex.costmanager.service.datajpa;

import com.alex.costmanager.UserTestData;
import com.alex.costmanager.service.AbstractCostServiceTest;
import com.alex.model.Cost;
import com.alex.util.exception.NotFoundException;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

import static com.alex.Profiles.DATAJPA;
import static com.alex.costmanager.CostTestData.*;
import static com.alex.costmanager.UserTestData.ADMIN_ID;


@ActiveProfiles(DATAJPA)
public class DataJpaCostServiceTest extends AbstractCostServiceTest {

    @Test
    public void getWithUser()  {
        Cost adminCost = service.getWithUser(ADMIN_COST_ID, ADMIN_ID);
        assertMatch(adminCost, ADMIN_COST1);
        UserTestData.assertMatch(adminCost.getUser(), UserTestData.ADMIN);
    }

    @Test(expected = NotFoundException.class)
    public void getWithUserNotFound()  {
        service.getWithUser(1, ADMIN_ID);
    }
}
