package az.developia.balance_management.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ExpenseResponse {
    private List<ExpenseSingleResponse> expenses;
}
