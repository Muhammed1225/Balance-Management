package az.developia.balance_management.service.impl;

import az.developia.balance_management.dto.request.ExpenseAddRequest;
import az.developia.balance_management.dto.request.ExpenseUpdateRequest;
import az.developia.balance_management.dto.response.ExpenseResponse;
import az.developia.balance_management.dto.response.ExpenseSingleResponse;
import az.developia.balance_management.dto.response.IncomeResponse;
import az.developia.balance_management.dto.response.IncomeSingleResponse;
import az.developia.balance_management.enums.CategoryTypes;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.model.CategoryEntity;
import az.developia.balance_management.model.ExpenseEntity;
import az.developia.balance_management.model.ExpenseWithCategoriesEntity;
import az.developia.balance_management.repository.CategoryRepository;
import az.developia.balance_management.repository.ExpenseRepository;
import az.developia.balance_management.repository.ExpenseWithCategoriesRepository;
import az.developia.balance_management.service.inter.ExpenseInter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService implements ExpenseInter {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ExpenseWithCategoriesRepository expenseWithCategoriesRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void add(ExpenseAddRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CategoryEntity> optional = categoryRepository.findById(request.getCategoryId());

        if (optional.isPresent()) {
            CategoryEntity category = optional.get();
            if (category.getUsername().equals(username)) {
                if (category.getType().equals(CategoryTypes.EXPENSE)) {
                    ExpenseEntity expense = new ExpenseEntity();
                    expense.setUsername(username);
                    expense.setDate(request.getDate());
                    expense.setAmount(request.getAmount());
                    expense.setCategoryId(request.getCategoryId());
                    repository.save(expense);
                } else {
                    throw new MyException("This isn't 'EXPENSE' category!");
                }
            } else {
                throw new MyException("The category wasn't found!");
            }
        } else {
            throw new MyException("The category wasn't found!");
        }
    }

    @Override
    public ExpenseResponse findExpenses(LocalDate beginDate, LocalDate endDate) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ExpenseWithCategoriesEntity> entities = expenseWithCategoriesRepository.filter(beginDate, endDate, username);
        return converter(entities);
    }

    @Override
    public void update(ExpenseUpdateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ExpenseEntity> optional = repository.findById(request.getId());

        if (optional.isPresent()) {
            ExpenseEntity expense = optional.get();
            if (expense.getUsername().equals(username)) {
                Optional<CategoryEntity> optionalCategory = categoryRepository.findById(request.getCategoryId());

                if (optionalCategory.isPresent()) {
                    CategoryEntity category = optionalCategory.get();

                    if (category.getUsername().equals(username)) {
                        if (category.getType().equals(CategoryTypes.EXPENSE)) {
                            expense.setAmount(request.getAmount());
                            expense.setDate(request.getDate());
                            expense.setCategoryId(request.getCategoryId());
                            repository.save(expense);
                        } else {
                            throw new MyException("This isn't 'EXPENSE' category!");
                        }
                    } else {
                        throw new MyException("The category wasn't found!");
                    }
                } else  {
                    throw new MyException("The category wasn't found!");
                }
            } else {
                throw new MyException("The expense wasn't found!");
            }
        } else {
            throw new MyException("The expense wasn't found!");
        }
    }

    @Override
    public void delete(Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ExpenseEntity> optional = repository.findById(id);

        if (optional.isPresent()) {
            ExpenseEntity expense = optional.get();
            if (expense.getUsername().equals(username)) {
                repository.delete(expense);
            } else {
                throw new MyException("The expense wasn't found!");
            }
        } else {
            throw new MyException("The expense wasn't found!");
        }
    }

    @Override
    public ExpenseResponse findExpense(Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<ExpenseWithCategoriesEntity> optional = expenseWithCategoriesRepository.findExpenseById(id);
        List<ExpenseSingleResponse> responseList = new ArrayList<>();

        if (optional.isPresent()) {
            ExpenseWithCategoriesEntity expense = optional.get();
            if (expense.getUsername().equals(username)) {
                ExpenseSingleResponse singleResponse = new ExpenseSingleResponse();
                mapper.map(expense, singleResponse);
                responseList.add(singleResponse);
            } else {
                throw new MyException("The expense wasn't found!");
            }
        } else {
            throw new MyException("The expense wasn't found!");
        }

        ExpenseResponse response = new ExpenseResponse();
        response.setExpenses(responseList);
        return response;
    }

    private ExpenseResponse converter(List<ExpenseWithCategoriesEntity> entities) {
        ExpenseResponse response = new ExpenseResponse();
        List<ExpenseSingleResponse> responseList = new ArrayList<>();

        for (ExpenseWithCategoriesEntity e : entities) {
            ExpenseSingleResponse singleResponse = new ExpenseSingleResponse();
            mapper.map(e, singleResponse);
            responseList.add(singleResponse);
        }

        response.setExpenses(responseList);
        return response;
    }

}
