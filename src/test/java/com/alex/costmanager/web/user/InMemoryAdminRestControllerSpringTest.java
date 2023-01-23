package com.alex.costmanager.web.user;

import com.alex.costmanager.repository.inmemory.InMemoryUserRepository;
import com.alex.util.exception.NotFoundException;
import com.alex.web.user.AdminRestController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.alex.costmanager.UserTestData.USER_ID;


@ContextConfiguration({"classpath:spring/spring-app.xml", "classpath:spring/inmemory.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class InMemoryAdminRestControllerSpringTest {

    @Autowired
    private AdminRestController controller;

    @Autowired
    private InMemoryUserRepository repository;

    @Before
    public void setUp() {
        repository.init();
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
        controller.delete(USER_ID);
        controller.get(USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        controller.delete(10);
    }
}
