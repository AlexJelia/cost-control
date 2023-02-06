package com.alex.web;

import com.alex.service.CostService;
import com.alex.util.CostsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {

    @Autowired
    private CostService costService;

    @GetMapping("/")
    public String root() {
        return "redirect:costs";
    }

    @GetMapping("/users")
    public String getUsers() {
        return "users";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @GetMapping("/costs")
    public String getCosts(Model model) {
        model.addAttribute("costs",
                CostsUtil.getTransferObjects(costService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCostsPerDay()));
        return "costs";
    }
}