package com.alex.web;

import com.alex.util.CostsUtil;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class CostServlet extends HttpServlet {
    private static final Logger log = getLogger(CostServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("get costs");
        req.setAttribute("costsList", CostsUtil.getWithExcess(CostsUtil.costsList,2000));
        req.getRequestDispatcher("/costs.jsp").forward(req, resp);
    }
}
