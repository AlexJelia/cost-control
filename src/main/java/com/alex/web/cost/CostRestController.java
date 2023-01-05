package com.alex.web.cost;

import com.alex.model.Cost;
import com.alex.service.CostService;
import com.alex.to.CostTo;
import com.alex.util.CostsUtil;
import com.alex.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.alex.util.ValidationUtil.assureIdConsistent;
import static com.alex.util.ValidationUtil.checkNew;

@Controller
public class CostRestController {
    private static final Logger log = LoggerFactory.getLogger(CostRestController.class);
    private CostService service;

    public CostRestController(CostService service) {
        this.service = service;
    }

    public Cost get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<CostTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return CostsUtil.getTransferObjects(service.getAll(userId), SecurityUtil.authUserCostsPerDay());
    }

    public Cost create(Cost cost) {
        int userId = SecurityUtil.authUserId();
        checkNew(cost);
        log.info("create {} for user {}", cost, userId);
        return service.create(cost, userId);
    }

    public void update(Cost cost, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(cost, id);
        log.info("update {} for user {}", cost, userId);
        service.update(cost, userId);
    }

    public List<CostTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Cost> mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId);
        return CostsUtil.getFilteredTransferObjects(mealsDateFiltered, startTime, endTime,SecurityUtil.authUserCostsPerDay());
    }


}