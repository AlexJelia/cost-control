package com.alex.costmanager.service;

import com.alex.Profiles;
import com.alex.model.Cost;
import com.alex.service.CostService;
import com.alex.util.exception.NotFoundException;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.TimeUnit;

import static com.alex.costmanager.CostTestData.*;
import static com.alex.costmanager.UserTestData.ADMIN_ID;
import static com.alex.costmanager.UserTestData.USER_ID;
import static org.slf4j.LoggerFactory.getLogger;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(Profiles.JDBC)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class CostServiceTest {

    private static final Logger log = getLogger("result");

    private static StringBuilder results = new StringBuilder();

    //todo change to junit5 extensions
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            log.info(result + " ms\n");
        }
    };

    @AfterClass
    public static void printResult() {
        log.info("\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------" +
                results +
                "\n---------------------------------");
    }

    @Autowired
    private CostService service;

    @Test
    public void delete() throws Exception {
        service.delete(COST1_ID, USER_ID);
        thrown.expect(NotFoundException.class);
        service.get(COST1_ID, USER_ID);
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(1, USER_ID);
    }

    @Test
    public void deleteNotOwn() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(COST1_ID, ADMIN_ID);
    }

    @Test
    public void create() throws Exception {
        Cost newCost = getNew();
        Cost created = service.create(newCost, USER_ID);
        Integer newId = created.getId();
        newCost.setId(newId);
        assertMatch(created, newCost);
        assertMatch(service.get(newId, USER_ID), newCost);
    }

    @Test
    public void get() throws Exception {
        Cost actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_COST1);
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(1, ADMIN_ID);
    }

    @Test
    public void getNotOwn() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(COST1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Cost updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(COST1_ID, USER_ID), updated);
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage("Not found entity with id=" + COST1_ID);
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
                LocalDate.of(2022, Month.MAY, 30), USER_ID),COST4, COST3, COST2, COST1);
    }

    @Test
    public void getBetweenWithNullDates() throws Exception {
        assertMatch(service.getBetweenDates(null, null, USER_ID), COSTS);
    }
}