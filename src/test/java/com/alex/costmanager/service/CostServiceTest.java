package com.alex.costmanager.service;

import com.alex.model.Cost;
import com.alex.service.CostService;
import com.alex.util.exception.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.Month;

import static com.alex.costmanager.CostTestData.*;
import static com.alex.costmanager.UserTestData.ADMIN_ID;
import static com.alex.costmanager.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class CostServiceTest {

    @Autowired
    private CostService service;

    @Test
    public void delete() throws Exception {
        service.delete(COST1_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), COST6, COST5, COST4, COST3, COST2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotOwn() throws Exception {
        service.delete(COST1_ID, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Cost newMeal = getCreated();
        Cost created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(newMeal, created);
        assertMatch(service.getAll(USER_ID), newMeal, COST6, COST5, COST4, COST3, COST2, COST1);
    }

    @Test
    public void get() throws Exception {
        Cost actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_COST1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void getNotOwn() throws Exception {
        service.get(COST1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Cost updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(COST1_ID, USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception {
        service.update(COST1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        assertMatch(service.getAll(USER_ID), COSTS);
    }

    @Test
    public void getBetween() throws Exception {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2022, Month.MAY, 30),
                LocalDate.of(2022, Month.MAY, 30), USER_ID), COST3, COST2, COST1);
    }
}