package com.ecommerce.cara.spec;

import com.ecommerce.cara.entity.Product;
import com.ecommerce.cara.entity.ProductDetails;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductSpecifications {
    public static Specification<Product> byCriteria(Integer brand, Integer category, String sort, Double minPrice, Double maxPrice, String size) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (brand != null) {
                predicates.add(builder.equal(root.get("brand").get("id"), brand));
            }

            if (category != null) {
                predicates.add(builder.equal(root.get("category").get("id"), category));
            }


            if (size != null) {
                Join<Product, ProductDetails> productDetailsJoin = root.join("productDetailsList");
                predicates.add(builder.equal(productDetailsJoin.get("size"), size));
            }

            if (minPrice != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if(maxPrice != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (sort != null) {
                if (sort.equalsIgnoreCase("asc")) {
                    query.orderBy(builder.asc(root.get("price")));
                } else if (sort.equalsIgnoreCase("desc")) {
                    query.orderBy(builder.desc(root.get("price")));
                }
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Product> hasSearchQuery(String searchQuery) {
        return (root, query, criteriaBuilder) -> {
            if (searchQuery == null || searchQuery.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.like(root.get("productName"), "%" + searchQuery + "%"));
            predicates.add(criteriaBuilder.like(root.get("description"), "%" + searchQuery + "%"));
            predicates.add(criteriaBuilder.like(root.get("category").get("categoryName"), "%" + searchQuery + "%"));
            predicates.add(criteriaBuilder.like(root.get("brand").get("brandName"), "%" + searchQuery + "%"));

            try {
                int productId = Integer.parseInt(searchQuery);
                predicates.add(criteriaBuilder.equal(root.get("productId"), productId));
            } catch (NumberFormatException e) {
                // Not a number, ignore productId search
            }

            try {
                double price = Double.parseDouble(searchQuery);
                predicates.add(criteriaBuilder.equal(root.get("price"), price));
            } catch (NumberFormatException e) {
                // Not a number, ignore price search
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
