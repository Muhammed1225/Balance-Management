package az.developia.balance_management.service.inter;

import az.developia.balance_management.dto.response.ReportResponse;

import java.time.LocalDate;

public interface ReportInter {

    Double totalExpense(LocalDate beginDate, LocalDate endDate);

    Double calculateBudget(LocalDate beginDate, LocalDate endDate);

    Double totalIncome(LocalDate beginDate, LocalDate endDate);

    ReportResponse matchPlans();

}
