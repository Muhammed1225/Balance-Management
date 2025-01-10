package az.developia.balance_management.controller;

import az.developia.balance_management.dto.request.IncomeAddRequest;
import az.developia.balance_management.dto.request.IncomeUpdateRequest;
import az.developia.balance_management.dto.response.IncomeResponse;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.service.impl.IncomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    private IncomeService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addIncome(@Valid @RequestBody IncomeAddRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.add(request);
        return ResponseEntity.ok("The income was added!");
    }

    @GetMapping
    public IncomeResponse findIncomes(@RequestParam(name = "beginDate", required = false) LocalDate beginDate,
                                      @RequestParam(name = "endDate", required = false) LocalDate endDate) {
        LocalDate now = LocalDate.now();
        if (beginDate == null) {
            beginDate = now.withDayOfMonth(1);
        }
        if (endDate == null) {
            endDate = now.withDayOfMonth(now.lengthOfMonth());
        }
        return service.findIncomes(beginDate, endDate);
    }

    @GetMapping("/{id}")
    public IncomeResponse findIncome(@PathVariable Integer id) {
        return service.findIncome(id);
    }

    @PutMapping
    public ResponseEntity<String> updateIncome(@Valid @RequestBody IncomeUpdateRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.update(request);
        return ResponseEntity.ok("The income was updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("The income was deleted!");
    }

}
