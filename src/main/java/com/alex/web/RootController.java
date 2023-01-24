package com.alex.web;

import com.alex.service.CostService;
import com.alex.service.UserService;
import com.alex.util.CostsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootController {
    @Autowired
    private UserService userService;

    @Autowired
    private CostService costService;

    @GetMapping("/")
    public String root() {
        return "index";
    }

    @GetMapping("/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @PostMapping("/users")
    public String setUser(HttpServletRequest request) {
        int userId = Integer.parseInt(request.getParameter("userId"));
        SecurityUtil.setAuthUserId(userId);
        return "redirect:costs";
    }

    @GetMapping("/costs")
    public String getCosts(Model model) {
        model.addAttribute("costs",
                CostsUtil.getTransferObjects(costService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCostsPerDay()));
        return "costs";
    }
}