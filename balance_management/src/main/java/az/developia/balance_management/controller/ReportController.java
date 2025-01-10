package az.developia.balance_management.controller;

import az.developia.balance_management.dto.response.ReportResponse;
import az.developia.balance_management.service.impl.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService service;

    @GetMapping("/total-income")
    public Double totalIncome(@RequestParam(name = "beginDate", required = false) LocalDate beginDate,
                             @RequestParam(name = "endDate", required = false) LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (beginDate == null) {
            beginDate = now.withDayOfMonth(1);
        }
        if (endDate == null) {
            endDate = now;
        }
        return service.totalIncome(beginDate, endDate);
    }

    @GetMapping("/total-expense")
    public Double totalExpense(@RequestParam(name = "beginDate", required = false) LocalDate beginDate,
                              @RequestParam(name = "endDate", required = false) LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (beginDate == null) {
            beginDate = now.withDayOfMonth(1);
        }
        if (endDate == null) {
            endDate = now;
        }
        return service.totalExpense(beginDate, endDate);
    }

    @GetMapping("/budget")
    public Double findBudget(@RequestParam(name = "beginDate", required = false) LocalDate beginDate,
                              @RequestParam(name = "endDate", required = false) LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (beginDate == null) {
            beginDate = now.withDayOfMonth(1);
        }
        if (endDate == null) {
            endDate = now;
        }
        return service.calculateBudget(beginDate, endDate);
    }

    @GetMapping
    public ReportResponse matchPlan() {
        return service.matchPlans();
    }

}
