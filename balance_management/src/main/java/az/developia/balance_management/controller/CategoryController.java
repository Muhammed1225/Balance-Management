package az.developia.balance_management.controller;

import az.developia.balance_management.dto.request.CategoryAddRequest;
import az.developia.balance_management.dto.request.CategoryUpdateRequest;
import az.developia.balance_management.dto.response.CategoryResponse;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.service.impl.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@Valid @RequestBody CategoryAddRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.add(request);
    }

    @GetMapping("/find-all")
    public CategoryResponse findCategories() {
        return service.findCategories();
    }

    @GetMapping("/{id}")
    public CategoryResponse findCategory(@PathVariable Integer id) {
        return service.findCategory(id);
    }

    @GetMapping("/search")
    public CategoryResponse searchCategory(@RequestParam(name = "name", required = false, defaultValue = "") String name,
                                           @RequestParam(name = "type", required = false, defaultValue = "") String type) {
        return service.filterCategory(name, type);
    }

    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody CategoryUpdateRequest request, BindingResult br) {
        if (br.hasErrors()) {
            throw new MyException("There is a problem in sent data!", br);
        }
        service.update(request);
        return ResponseEntity.ok("The category was updated!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.ok("The category was deleted!");
    }

}
