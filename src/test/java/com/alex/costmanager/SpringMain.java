package com.alex.costmanager;

import com.alex.model.Role;
import com.alex.model.User;
import com.alex.to.CostTo;
import com.alex.web.cost.CostRestController;
import com.alex.web.user.AdminRestController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {


        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "Hank", "email@mail.ru", "password", Role.ROLE_ADMIN));

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
