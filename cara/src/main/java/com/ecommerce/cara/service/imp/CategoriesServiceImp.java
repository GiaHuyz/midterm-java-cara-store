package com.ecommerce.cara.service.imp;

import com.ecommerce.cara.dto.CategoryDTO;
import com.ecommerce.cara.entity.Category;
import com.ecommerce.cara.repository.CategoriesRepository;
import com.ecommerce.cara.service.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoriesServiceImp implements CategoriesService {

    @Autowired
    CategoriesRepository categoriesRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        List<Category> categories = categoriesRepository.findAll();
        for (Category category: categories) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(category.getCategoryName());
            categoryDTO.setId(category.getCategoryId());
            categoryDTOList.add(categoryDTO);
        }
        return categoryDTOList;
    }
}
