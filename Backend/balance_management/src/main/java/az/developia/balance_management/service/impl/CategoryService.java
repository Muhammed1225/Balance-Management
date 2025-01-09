package az.developia.balance_management.service.impl;

import az.developia.balance_management.dto.request.CategoryAddRequest;
import az.developia.balance_management.dto.request.CategoryUpdateRequest;
import az.developia.balance_management.dto.response.CategoryResponse;
import az.developia.balance_management.dto.response.CategorySingleResponse;
import az.developia.balance_management.exception.MyException;
import az.developia.balance_management.model.CategoryEntity;
import az.developia.balance_management.repository.CategoryRepository;
import az.developia.balance_management.service.inter.CategoryInter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryInter {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void add(CategoryAddRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        CategoryEntity category = new CategoryEntity();
        mapper.map(request, category);
        category.setUsername(username);
        repository.save(category);
    }

    @Override
    public CategoryResponse findCategories() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<CategoryEntity> entities = repository.findAllByUsername(username);
        return converter(entities);
    }

    @Override
    public CategoryResponse filterCategory(String name, String type) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        name = name.toLowerCase().trim();
        type = type.toUpperCase().trim();
        List<CategoryEntity> entities = repository.searchCategory(name, type, username);
        return converter(entities);
    }

    @Override
    public void update(CategoryUpdateRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CategoryEntity> optional = repository.findById(request.getId());

        if (optional.isPresent()) {
            CategoryEntity entity = optional.get();
            if (entity.getUsername().equals(username)) {
                mapper.map(request, entity);
                repository.save(entity);
            } else {
                throw new MyException("The category wasn't found!");
            }
        } else {
            throw new MyException("The category wasn't found!");
        }
    }

    @Override
    public void delete(Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CategoryEntity> optional = repository.findById(id);

        if (optional.isPresent()) {
            CategoryEntity entity = optional.get();
            if (entity.getUsername().equals(username)) {
                repository.delete(entity);
            } else {
                throw new MyException("The category wasn't found!");
            }
        } else {
            throw new MyException("The category wasn't found!");
        }
    }

    @Override
    public CategoryResponse findCategory(Integer id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Optional<CategoryEntity> optional = repository.findById(id);
        List<CategoryEntity> entities = new ArrayList<>();

        if (optional.isPresent()) {
            CategoryEntity entity = optional.get();
            if (entity.getUsername().equals(username)) {
                entities.add(entity);
            } else {
                throw new MyException("The category wasn't found!");
            }
        } else {
            throw new MyException("The category wasn't found!");
        }

        return converter(entities);
    }

    public CategoryResponse converter(List<CategoryEntity> entities) {
        CategoryResponse response = new CategoryResponse();
        List<CategorySingleResponse> responseList = new ArrayList<>();

        for (CategoryEntity e : entities) {
            CategorySingleResponse singleResponse = new CategorySingleResponse();
            mapper.map(e, singleResponse);
            responseList.add(singleResponse);
        }

        response.setCategories(responseList);
        return response;
    }

}
