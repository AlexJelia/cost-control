package com.alex.costmanager.web.json;


import com.alex.costmanager.UserTestData;
import com.alex.model.Cost;
import com.alex.model.User;
import com.alex.web.json.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.alex.costmanager.CostTestData.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonUtilTest {

    @Test
    void readWriteValue()  {
        String json = JsonUtil.writeValue(ADMIN_COST1);
        System.out.println(json);
        Cost cost = JsonUtil.readValue(json, Cost.class);
        assertMatch(cost, ADMIN_COST1);
    }

    @Test
    void readWriteValues()  {
        String json = JsonUtil.writeValue(COSTS);
        System.out.println(json);
        List<Cost> costs = JsonUtil.readValues(json, Cost.class);
        assertMatch(costs, COSTS);
    }

    @Test
    void testWriteOnlyAccess() throws Exception {
        String json = JsonUtil.writeValue(UserTestData.USER);
        System.out.println(json);
        assertThat(json, not(containsString("password")));
        String jsonWithPass = UserTestData.jsonWithPassword(UserTestData.USER, "newPass");
        System.out.println(jsonWithPass);
        User user = JsonUtil.readValue(jsonWithPass, User.class);
        assertEquals(user.getPassword(), "newPass");
    }
}