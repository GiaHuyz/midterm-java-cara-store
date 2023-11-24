package com.ecommerce.cara.service.imp;

import com.ecommerce.cara.dto.BrandsDTO;
import com.ecommerce.cara.dto.CategoryDTO;
import com.ecommerce.cara.entity.Brands;
import com.ecommerce.cara.entity.Category;
import com.ecommerce.cara.repository.BrandsRepository;
import com.ecommerce.cara.service.BrandsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandsServiceImp implements BrandsService {

    @Autowired
    BrandsRepository brandsRepository;

    @Override
    public List<BrandsDTO> getAllBrands() {
        List<BrandsDTO> brandsDTOList = new ArrayList<>();
        List<Brands> brands = brandsRepository.findAll();
        for (Brands brand: brands) {
            BrandsDTO categoryDTO = new BrandsDTO();
            categoryDTO.setName(brand.getBrandName());
            categoryDTO.setId(brand.getBrandId());
            brandsDTOList.add(categoryDTO);
        }
        return brandsDTOList;
    }
}
