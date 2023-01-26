package com.alex.costmanager.service;

import com.alex.model.Cost;
import com.alex.service.CostService;
import com.alex.util.exception.NotFoundException;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static com.alex.costmanager.CostTestData.*;
import static com.alex.costmanager.UserTestData.ADMIN_ID;
import static com.alex.costmanager.UserTestData.USER_ID;
import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractCostServiceTest extends AbstractServiceTest {
    @Autowired
    protected CostService service;

    @Test
    void delete() {
        service.delete(COST1_ID, USER_ID);
        assertThrows(NotFoundException.class, () ->
                service.get(COST1_ID, USER_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.delete(1, USER_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () ->
                service.delete(COST1_ID, ADMIN_ID));
    }

    @Test
    void create() {
        Cost newCost = getNew();
        Cost created = service.create(newCost, USER_ID);
        Integer newId = created.getId();
        newCost.setId(newId);
        assertMatch(created, newCost);
        assertMatch(service.get(newId, USER_ID), newCost);
    }

    @Test
    void get() {
        Cost actual = service.get(ADMIN_COST_ID, ADMIN_ID);
        assertMatch(actual, ADMIN_COST1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () ->
                service.get(1, ADMIN_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () ->
                service.get(COST1_ID, ADMIN_ID));
    }

    @Test
    void update() {
        Cost updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(COST1_ID, USER_ID), updated);
    }

    @Test
    void updateNotFound() {
        NotFoundException e = assertThrows(NotFoundException.class, () -> service.update(COST1, ADMIN_ID));
        assertEquals(e.getMessage(), "Not found entity with id=" + COST1_ID);
    }

    @Test
    void getAll() {
        assertMatch(service.getAll(USER_ID), COSTS);
    }

    @Test
    void getBetween() {
        assertMatch(service.getBetweenDates(
                LocalDate.of(2022, Month.MAY, 30),
                LocalDate.of(2022, Month.MAY, 30), USER_ID), COST4, COST3, COST2, COST1);
    }

    @Test
    void getBetweenWithNullDates() {
        assertMatch(service.getBetweenDates(null, null, USER_ID), COSTS);
    }

    @Test
    public void createWithException() {
        Assumptions.assumeTrue(isJpaBased(), "Validation not supported (JPA only)");
        validateRootCause(() -> service.create(new Cost(null, of(2022, Month.JUNE, 1, 18, 0), "  ", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Cost(null, null, "Description", 300), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Cost(null, of(2022, Month.JUNE, 1, 18, 0), "Description", 0), USER_ID), ConstraintViolationException.class);
        validateRootCause(() -> service.create(new Cost(null, of(2022, Month.JUNE, 1, 18, 0), "Description", 10000000), USER_ID), ConstraintViolationException.class);
    }
}