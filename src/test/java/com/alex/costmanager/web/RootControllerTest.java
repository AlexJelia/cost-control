package com.alex.costmanager.web;

import com.alex.model.User;
import com.alex.web.SecurityUtil;
import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.alex.costmanager.CostTestData.COSTS;
import static com.alex.costmanager.UserTestData.*;
import static com.alex.util.CostsUtil.getTransferObjects;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void getUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"));
    }

    @Test
    void testMeals() throws Exception {
        mockMvc.perform(get("/costs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("costs"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/costs.jsp"))
                .andExpect(model().attribute("costs",getTransferObjects(COSTS, SecurityUtil.authUserCostsPerDay())));
    }
}