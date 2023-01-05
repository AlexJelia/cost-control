package com.alex.web;

import com.alex.model.Cost;
import com.alex.repository.CostRepository;
import com.alex.repository.inmemory.InMemoryCostRepository;
import com.alex.util.CostsUtil;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class CostServlet extends HttpServlet {
    private static final Logger log = getLogger(CostServlet.class);
    private CostRepository repository;

    @Override
    public void init() {
        repository = new InMemoryCostRepository();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Cost cost = new Cost(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("cost")));

        log.info(cost.isNew() ? "Create {}" : "Update {}", cost);
        repository.save(cost, SecurityUtil.authUserId());
        response.sendRedirect("costs");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                repository.delete(id, SecurityUtil.authUserId());
                response.sendRedirect("costs");
                break;
            case "create":
            case "update":
                final Cost cost = "create".equals(action) ?
                        new Cost(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        repository.get(getId(request), SecurityUtil.authUserId());
                request.setAttribute("cost", cost);
                request.getRequestDispatcher("/costForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("costsList", CostsUtil.getWithExcess(repository.getAll(SecurityUtil.authUserId()), CostsUtil.DEFAULT_COSTS_PER_DAY));
                request.getRequestDispatcher("/costs.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
