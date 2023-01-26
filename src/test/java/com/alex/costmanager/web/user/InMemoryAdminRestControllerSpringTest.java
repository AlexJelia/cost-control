package com.alex.costmanager.web.user;

import com.alex.costmanager.repository.inmemory.InMemoryUserRepository;
import com.alex.util.exception.NotFoundException;
import com.alex.web.user.AdminRestController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static com.alex.costmanager.UserTestData.USER_ID;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringJUnitConfig(locations = {"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
class InMemoryAdminRestControllerSpringTest {


    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @BeforeEach
    public void setUp() {
        repository.init();
    }

    @Test
    void delete() throws Exception {
        controller.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> controller.get(USER_ID));
    }

    @Test
    void deleteNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> controller.delete(10));
    }
}
