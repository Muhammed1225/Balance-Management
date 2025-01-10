package az.developia.balance_management.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ReportResponse {
    private List<ReportSingleResponse> reports;
}
