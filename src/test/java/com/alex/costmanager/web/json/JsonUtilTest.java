package com.alex.costmanager.web.json;


import com.alex.model.Cost;
import com.alex.web.json.JsonUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.alex.costmanager.CostTestData.*;

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
}