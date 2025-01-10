package az.developia.balance_management.service.inter;

import az.developia.balance_management.dto.request.IncomeAddRequest;
import az.developia.balance_management.dto.request.IncomeUpdateRequest;
import az.developia.balance_management.dto.response.IncomeResponse;

import java.time.LocalDate;

public interface IncomeInter {

    void add(IncomeAddRequest request);

    IncomeResponse findIncomes(LocalDate beginDate, LocalDate endDate);

    void update(IncomeUpdateRequest request);

    void delete(Integer id);

    IncomeResponse findIncome(Integer id);

}
