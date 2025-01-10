package az.developia.balance_management.controller;

import az.developia.balance_management.dto.request.PlanAddRequest;
import az.developia.balance_management.dto.request.PlanUpdateRequest;
import az.developia.balance_management.dto.response.PlanResponse;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.service.impl.PlanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService service;

    @PostMapping
    public ResponseEntity<String> addPlan(@Valid @RequestBody PlanAddRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.add(request);
        return ResponseEntity.ok("The plan was added!");
    }

    @GetMapping
    public PlanResponse findPlans() {
        return service.findPlans();
    }

    @GetMapping("/{id}")
    public PlanResponse findPlan(@PathVariable Integer id) {
        return service.findPlan(id);
    }

    @PutMapping
    public ResponseEntity<String> updatePlan(@Valid @RequestBody PlanUpdateRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.update(request);
        return ResponseEntity.ok("The plan was updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("The plan was deleted!");
    }

}
