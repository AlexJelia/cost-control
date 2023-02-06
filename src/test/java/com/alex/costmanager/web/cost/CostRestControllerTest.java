package com.alex.costmanager.web.cost;


import com.alex.costmanager.CostTestData;
import com.alex.costmanager.web.AbstractControllerTest;
import com.alex.model.Cost;
import com.alex.service.CostService;
import com.alex.web.cost.CostRestController;
import com.alex.web.json.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.alex.costmanager.CostTestData.assertMatch;
import static com.alex.costmanager.CostTestData.contentJson;
import static com.alex.costmanager.CostTestData.*;
import static com.alex.costmanager.TestUtil.*;
import static com.alex.costmanager.UserTestData.*;
import static com.alex.model.AbstractBaseEntity.START_SEQ;
import static com.alex.util.CostsUtil.createTo;
import static com.alex.util.CostsUtil.getTransferObjects;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class CostRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = CostRestController.REST_URL + '/';

    @Autowired
    private CostService costService;

    @Test
    void get() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + ADMIN_COST_ID)
                        .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertMatch(readFromJsonMvcResult(result, Cost.class), ADMIN_COST1));
    }

    @Test
    void getUnauth() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + COST1_ID))
                .andExpect(status().isUnauthorized());
    }


    @Test
    void delete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + COST1_ID)
                        .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());
        assertMatch(costService.getAll(START_SEQ), COST7,COST6, COST5, COST4, COST3, COST2);
    }

    @Test
    void update() throws Exception {
        Cost updated = CostTestData.getUpdated();

        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + COST1_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.writeValue(updated))
                        .with(userHttpBasic(USER)))
                .andExpect(status().isNoContent());

        assertMatch(costService.get(COST1_ID, START_SEQ), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Cost newCost = CostTestData.getNew();
        ResultActions action = mockMvc.perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newCost))
                .with(userHttpBasic(ADMIN)));
        Cost returned = readFromJson(action, Cost.class);
        Integer newId = returned.getId();
        newCost.setId(newId);
        assertMatch(returned, newCost);
        assertMatch(costService.getAll(ADMIN_ID), ADMIN_COST2, newCost, ADMIN_COST1);
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL)
                        .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(getTransferObjects(COSTS, USER.getCostsPerDay())));
    }

    @Test
    void filter() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                        .param("startDate", "2022-05-30").param("startTime", "07:00")
                        .param("endDate", "2022-05-31").param("endTime", "11:00")
                        .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(contentJson(createTo(COST5, true), createTo(COST1, false)));
    }

    @Test
    void filterAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&endTime=")
                        .with(userHttpBasic(USER)))
                .andExpect(status().isOk())
                .andExpect(contentJson(getTransferObjects(COSTS, USER.getCostsPerDay())));
    }
}