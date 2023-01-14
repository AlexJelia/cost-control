package com.alex.costmanager;


import com.alex.model.Cost;

import java.time.Month;
import java.util.List;

import static com.alex.model.AbstractBaseEntity.START_SEQ;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.Assertions.assertThat;


public class CostTestData {
    public static final int COST1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 9;

    public static final Cost COST1 = new Cost(COST1_ID, of(2022, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Cost COST2 = new Cost(COST1_ID + 1, of(2022, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Cost COST3 = new Cost(COST1_ID + 2, of(2022, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Cost COST4 = new Cost(COST1_ID + 3, of(2022, Month.MAY, 31, 0, 0), "граничное значение", 100);
    public static final Cost COST5 = new Cost(COST1_ID + 4, of(2022, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final Cost COST6 = new Cost(COST1_ID + 5, of(2022, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final Cost COST7 = new Cost(COST1_ID + 6, of(2022, Month.MAY, 31, 20, 0), "Ужин", 510);
    public static final Cost ADMIN_COST1 = new Cost(ADMIN_MEAL_ID, of(2022, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Cost ADMIN_COST2 = new Cost(ADMIN_MEAL_ID + 1, of(2022, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final List<Cost> COSTS = List.of(COST7,COST6, COST5, COST4, COST3, COST2, COST1);

    public static Cost getCreated() {
        return new Cost(null, of(2022, Month.JUNE, 1, 18, 0), "Созданный расход", 300);
    }

    public static Cost getUpdated() {
        return new Cost(COST1_ID, COST1.getDateTime(), "Обновленный завтрак", 200);
    }

    public static void assertMatch(Cost actual, Cost expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user");
    }

    public static void assertMatch(Iterable<Cost> actual, Cost... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Cost> actual, Iterable<Cost> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected);
    }
}
