package com.alex.costmanager;

import com.alex.Profiles;
import com.alex.to.CostTo;
import com.alex.web.cost.CostRestController;
import com.alex.web.user.AdminRestController;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {


        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles( Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/inmemory.xml");
            appCtx.refresh();

            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.getAll();
            System.out.println();

            CostRestController costController = appCtx.getBean(CostRestController.class);

            System.out.println(costController.getAll());

            List<CostTo> filteredCostsWithExcess =
                    costController.getBetween(
                            LocalDate.of(2020, Month.JANUARY, 1), LocalTime.of(7, 0),
                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));
            filteredCostsWithExcess.forEach(System.out::println);
        }
    }
}
