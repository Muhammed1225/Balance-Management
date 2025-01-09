package az.developia.balance_management.service.impl;

import az.developia.balance_management.dto.request.IncomeAddRequest;
import az.developia.balance_management.dto.request.IncomeUpdateRequest;
import az.developia.balance_management.dto.response.IncomeResponse;
import az.developia.balance_management.dto.response.IncomeSingleResponse;
import az.developia.balance_management.enums.CategoryTypes;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.model.CategoryEntity;
import az.developia.balance_management.model.IncomeEntity;
import az.developia.balance_management.model.IncomeWithCategoriesEntity;
import az.developia.balance_management.repository.CategoryRepository;
import az.developia.balance_management.repository.IncomeRepository;
import az.developia.balance_management.repository.IncomeWithCategoryRepository;
import az.developia.balance_management.service.inter.IncomeInter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IncomeService implements IncomeInter {

    @Autowired
    private IncomeRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private IncomeWithCategoryRepository incomeWithCategoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void add(IncomeAddRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CategoryEntity> optional = categoryRepository.findById(request.getCategoryId());

        if (optional.isPresent()) {
            CategoryEntity category = optional.get();
            if (category.getUsername().equals(username)) {
                if (category.getType().equals(CategoryTypes.INCOME)) {
                    IncomeEntity income = new IncomeEntity();
                    income.setAmount(request.getAmount());
                    income.setDate(request.getDate());
                    income.setCategoryId(request.getCategoryId());
                    income.setUsername(username);
                    repository.save(income);
                } else {
                    throw new MyException("This category isn't 'INCOME' category!");
                }
            } else {
                throw new MyException("The category wasn't found!");
            }
        } else {
            throw new MyException("The category wasn't found!");
        }
    }

    @Override
    public IncomeResponse findIncomes(LocalDate beginDate, LocalDate endDate) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<IncomeWithCategoriesEntity> entities = incomeWithCategoryRepository.filterIncomeCategories(username, beginDate, endDate);
        return converter(entities);
    }

    @Override
    public void update(IncomeUpdateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<IncomeEntity> optional = repository.findById(request.getId());

        if (optional.isPresent()) {
            IncomeEntity income = optional.get();
            if (income.getUsername().equals(username)) {
                Optional<CategoryEntity> optionalCategory = categoryRepository.findById(request.getCategoryId());

                if (optionalCategory.isPresent()) {
                    CategoryEntity category = optionalCategory.get();

                    if (category.getUsername().equals(username)) {
                        if (category.getType().equals(CategoryTypes.INCOME)) {
                            income.setAmount(request.getAmount());
                            income.setDate(request.getDate());
                            income.setCategoryId(request.getCategoryId());
                            repository.save(income);
                        } else {
                            throw new MyException("This isn't 'INCOME' category!");
                        }
                    } else {
                        throw new MyException("The category wasn't found!");
                    }
                } else  {
                    throw new MyException("The category wasn't found!");
                }
            } else {
                throw new MyException("The income wasn't found!");
            }
        } else {
            throw new MyException("The income wasn't found!");
        }
    }

    @Override
    public void delete(Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<IncomeEntity> optional = repository.findById(id);

        if (optional.isPresent()) {
            IncomeEntity income = optional.get();
            if (income.getUsername().equals(username)) {
                repository.delete(income);
            } else {
                throw new MyException("The income wasn't found!");
            }
        } else {
            throw new MyException("The income wasn't found!");
        }
    }

    @Override
    public IncomeResponse findIncome(Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<IncomeWithCategoriesEntity> optional = incomeWithCategoryRepository.findIncomeById(id);
        List<IncomeSingleResponse> responseList = new ArrayList<>();

        if (optional.isPresent()) {
            IncomeWithCategoriesEntity income = optional.get();
            if (income.getUsername().equals(username)) {
                IncomeSingleResponse singleResponse = new IncomeSingleResponse();
                mapper.map(income, singleResponse);
                responseList.add(singleResponse);
            } else {
                throw new MyException("The income wasn't found!");
            }
        } else {
            throw new MyException("The income wasn't found!");
        }

        IncomeResponse response = new IncomeResponse();
        response.setIncomes(responseList);
        return response;
    }

    private IncomeResponse converter(List<IncomeWithCategoriesEntity> entities) {
        IncomeResponse response = new IncomeResponse();
        List<IncomeSingleResponse> responseList = new ArrayList<>();

        for (IncomeWithCategoriesEntity e : entities) {
            IncomeSingleResponse singleResponse =new IncomeSingleResponse();
            mapper.map(e, singleResponse);
            responseList.add(singleResponse);
        }

        response.setIncomes(responseList);
        return response;
    }

}
