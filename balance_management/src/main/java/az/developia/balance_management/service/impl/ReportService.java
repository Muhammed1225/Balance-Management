package az.developia.balance_management.service.impl;

import az.developia.balance_management.dto.response.*;
import az.developia.balance_management.repository.ClientRepository;
import az.developia.balance_management.service.inter.ReportInter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReportService implements ReportInter {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private PlanService planService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private EmailService emailService;

    @Override
    public Double totalIncome(LocalDate beginDate, LocalDate endDate) {
        Double totalIncome = 0d;
        IncomeResponse incomes = incomeService.findIncomes(beginDate, endDate);
        for (IncomeSingleResponse income : incomes.getIncomes()) {
            totalIncome += income.getAmount();
        }
        return totalIncome;
    }

    @Override
    public ReportResponse matchPlans() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String email = clientRepository.findByUsername(username).getEmail();
        ReportResponse response = new ReportResponse();
        PlanResponse plans = planService.findPlans();
        List<ReportSingleResponse> responseList = new ArrayList<>();

        for (PlanSingleResponse p : plans.getPlans()) {
            if (p.getBeginDate().isBefore(LocalDate.now())) {
                ReportSingleResponse singleResponse = new ReportSingleResponse();
                LocalDate beginDate = p.getBeginDate();
                LocalDate endDate = p.getEndDate();
                Double budget;
                long todayDays;

                if (p.getEndDate().isBefore(LocalDate.now())) {
                    budget = calculateBudget(beginDate, endDate);
                    todayDays = ChronoUnit.DAYS.between(beginDate, endDate) + 1;
                } else {
                    budget = calculateBudget(beginDate, LocalDate.now());
                    todayDays = ChronoUnit.DAYS.between(beginDate, LocalDate.now()) + 1;
                }

                long planDays = ChronoUnit.DAYS.between(beginDate, endDate) + 1;
                double realBudget = p.getBudget()/planDays * todayDays;
                Double roundedBudget = Math.floor(realBudget * 10) / 10;

                p.setBudget(roundedBudget);
                mapper.map(p, singleResponse);

                Double difference = Math.floor((budget - p.getBudget()) * 100) / 100;
                singleResponse.setDifference(difference);
                singleResponse.setYourBudget(budget);
                responseList.add(singleResponse);

                if (p.getEndDate().isAfter((LocalDate.now()))) {
                    if (singleResponse.getDifference() < 0) {
                        String message = "You have exceeded your limit! As your plan your budget had to be " + p.getBudget()
                                + "0 azn, but your budget is " + budget + "0 azn. Your budget is " + (-singleResponse.getDifference()) +
                                "0 azn less than plan budget.";
                        emailService.sendEmail(email, "Exceeded Limit!", message);
                    } else {
                        String message = "You are very good at your plan! As your plan your budget had to be " + p.getBudget()
                                + "0 azn, but your budget is " + budget + "0 azn. Your budget is " + singleResponse.getDifference() +
                                "0 azn more than plan budget.";
                        emailService.sendEmail(email, "Keep it up!", message);
                    }
                }
            }
        }

        response.setReports(responseList);
        return response;
    }

    @Override
    public Double totalExpense(LocalDate beginDate, LocalDate endDate) {
        Double totalExpense = 0d;
        ExpenseResponse expenses = expenseService.findExpenses(beginDate, endDate);
        for (ExpenseSingleResponse expense : expenses.getExpenses()) {
            totalExpense += expense.getAmount();
        }
        return totalExpense;
    }

    @Override
    public Double calculateBudget(LocalDate beginDate, LocalDate endDate) {
        Double totalIncome = totalIncome(beginDate, endDate);
        Double totalExpense = totalExpense(beginDate, endDate);
        return (totalIncome - totalExpense);
    }

}
