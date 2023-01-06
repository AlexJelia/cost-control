package com.alex.web;

import com.alex.model.Cost;
import com.alex.repository.inmemory.InMemoryCostRepository;
import com.alex.util.CostsUtil;
import com.alex.web.cost.CostRestController;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class CostServlet extends HttpServlet {
    private ConfigurableApplicationContext springContext;
    private CostRestController costController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        springContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        costController = springContext.getBean(CostRestController.class);
    }

    @Override
    public void destroy() {
        springContext.close();
        super.destroy();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        request.setCharacterEncoding("UTF-8");

        Cost cost = new Cost(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("cost")));

        if(StringUtils.isEmpty(request.getParameter("id"))){
            costController.create(cost);
        }else{
            costController.update(cost,getId(request));
        }
        response.sendRedirect("costs");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                costController.delete(id);
                response.sendRedirect("costs");
                break;
            case "create":
            case "update":
                final Cost cost = "create".equals(action) ?
                        new Cost(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        costController.get(getId(request));
                request.setAttribute("cost", cost);
                request.getRequestDispatcher("/costForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                request.setAttribute("costsList", costController.getAll());
                request.getRequestDispatcher("/costs.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
