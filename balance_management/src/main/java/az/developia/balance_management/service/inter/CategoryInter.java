package az.developia.balance_management.service.inter;

import az.developia.balance_management.dto.request.CategoryAddRequest;
import az.developia.balance_management.dto.request.CategoryUpdateRequest;
import az.developia.balance_management.dto.response.CategoryResponse;
import az.developia.balance_management.model.CategoryEntity;

import java.util.List;

public interface CategoryInter {

    void add(CategoryAddRequest request);

    CategoryResponse findCategories();

    CategoryResponse filterCategory(String name, String type);

    void update(CategoryUpdateRequest request);

    void delete(Integer id);

    CategoryResponse findCategory(Integer id);

}
