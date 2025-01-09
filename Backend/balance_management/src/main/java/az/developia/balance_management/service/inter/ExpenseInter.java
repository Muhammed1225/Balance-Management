package az.developia.balance_management.service.inter;

import az.developia.balance_management.dto.request.ExpenseAddRequest;
import az.developia.balance_management.dto.request.ExpenseUpdateRequest;
import az.developia.balance_management.dto.response.ExpenseResponse;
import az.developia.balance_management.dto.response.IncomeResponse;

import java.time.LocalDate;

public interface ExpenseInter {

    void add(ExpenseAddRequest request);

    ExpenseResponse findExpenses(LocalDate beginDate, LocalDate endDate);

    void update(ExpenseUpdateRequest request);

    void delete(Integer id);

    ExpenseResponse findExpense(Integer id);

}
