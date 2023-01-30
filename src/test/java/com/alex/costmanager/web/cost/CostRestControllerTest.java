package com.alex.costmanager.web.cost;


import com.alex.costmanager.web.AbstractControllerTest;
import com.alex.model.Cost;
import com.alex.service.CostService;
import com.alex.util.exception.NotFoundException;
import com.alex.web.cost.CostRestController;
import com.alex.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.alex.costmanager.CostTestData.*;
import static com.alex.costmanager.TestUtil.readFromJson;
import static com.alex.costmanager.TestUtil.readFromJsonMvcResult;
import static com.alex.costmanager.UserTestData.USER;
import static com.alex.costmanager.UserTestData.USER_ID;
import static com.alex.model.AbstractBaseEntity.START_SEQ;
import static com.alex.util.CostsUtil.createTo;
import static com.alex.util.CostsUtil.getTransferObjects;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CostRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = CostRestController.REST_URL + '/';

    @Autowired
    private CostService costService;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + COST1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Cost.class), COST1));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + COST1_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> costService.get(COST1_ID, USER_ID));
    }

    @Test
    void update() throws Exception {
        Cost updated = getUpdated();

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + COST1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        assertMatch(costService.get(COST1_ID, START_SEQ), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Cost newCost = getNew();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newCost)));

        Cost created = readFromJson(action, Cost.class);
        Integer newId = created.getId();
        newCost.setId(newId);
        assertMatch(created, newCost);
        assertMatch(costService.get(newId, USER_ID), newCost);
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getTransferObjects(COSTS, USER.getCostsPerDay())));
    }

    @Test
    void filter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                        .param("startDate", "2022-05-30").param("startTime", "07:00")
                        .param("endDate", "2022-05-31").param("endTime", "11:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(createTo(COST5, true), createTo(COST1, false)));
    }

    @Test
    void filterAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&endTime="))
                .andExpect(status().isOk())
                .andExpect(contentJson(getTransferObjects(COSTS, USER.getCostsPerDay())));
    }
}