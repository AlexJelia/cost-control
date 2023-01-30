package com.alex.costmanager.service.datajpa;

import com.alex.costmanager.CostTestData;
import com.alex.costmanager.service.AbstractUserServiceTest;
import com.alex.model.User;
import com.alex.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static com.alex.Profiles.DATAJPA;
import static com.alex.costmanager.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ActiveProfiles(DATAJPA)
class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
     void getWithCosts()  {
        User admin = service.getWithCosts(ADMIN_ID);
        assertMatch(admin, ADMIN);
        CostTestData.assertMatch(admin.getCosts(), CostTestData.ADMIN_COST2, CostTestData.ADMIN_COST1);
    }

    @Test
     void getWithCostsNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.getWithCosts(1));
    }
    
}