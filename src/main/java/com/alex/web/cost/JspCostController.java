package com.alex.web.cost;

import com.alex.model.Cost;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static com.alex.util.TimeUtil.parseLocalDate;
import static com.alex.util.TimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/costs")
public class JspCostController extends AbstractCostController {

    @GetMapping("/delete")
    public String delete(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/costs";
    }

    @GetMapping("/update")
    public String update(HttpServletRequest request, Model model) {
        model.addAttribute("cost", super.get(getId(request)));
        return "costForm";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("cost", new Cost(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), "", 1000));
        return "costForm";
    }

    @PostMapping
    public String updateOrCreate(HttpServletRequest request) {
        Cost cost = new Cost(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("cost")));

        if (request.getParameter("id").isEmpty()) {
            super.create(cost);
        } else {
            super.update(cost, getId(request));
        }
        return "redirect:/costs";
    }

    @GetMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("costs", super.getBetween(startDate, startTime, endDate, endTime));
        return "costs";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
