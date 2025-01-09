package az.developia.balance_management.controller;

import az.developia.balance_management.dto.request.ExpenseAddRequest;
import az.developia.balance_management.dto.request.ExpenseUpdateRequest;
import az.developia.balance_management.dto.response.ExpenseResponse;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.service.impl.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @PostMapping
    public void addExpense(@Valid @RequestBody ExpenseAddRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.add(request);
    }

    @GetMapping
    public ExpenseResponse findExpenses(@RequestParam(name = "beginDate", required = false) LocalDate beginDate,
                                        @RequestParam(name = "endDate", required = false) LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (beginDate == null) {
            beginDate = now.withDayOfMonth(1);
        }
        if (endDate == null) {
            endDate = now.withDayOfMonth(now.lengthOfMonth());
        }
        return service.findExpenses(beginDate, endDate);
    }

    @GetMapping("/{id}")
    public ExpenseResponse findExpense(@PathVariable Integer id) {
        return service.findExpense(id);
    }

    @PutMapping
    public ResponseEntity<String> updateExpense(@Valid @RequestBody ExpenseUpdateRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.update(request);
        return ResponseEntity.ok("The expense was updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("The expense was deleted!");
    }

}
