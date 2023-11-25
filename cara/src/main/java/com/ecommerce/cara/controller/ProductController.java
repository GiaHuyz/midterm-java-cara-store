package com.ecommerce.cara.controller;

import com.ecommerce.cara.dto.DetailDTO;
import com.ecommerce.cara.dto.ProductDTO;
import com.ecommerce.cara.payload.ResponseData;
import com.ecommerce.cara.payload.request.DetailRequest;
import com.ecommerce.cara.payload.request.ProductRequest;
import com.ecommerce.cara.service.FileService;
import com.ecommerce.cara.service.ImagesService;
import com.ecommerce.cara.service.ProductDetailService;
import com.ecommerce.cara.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductDetailService productDetailService;

    @Autowired
    ImagesService imageService;

    @Autowired
    FileService fileService;

    @GetMapping()
    public ResponseEntity<?> getAllProducts(@RequestParam(required = false) Integer brand,
                                            @RequestParam(required = false) Integer category,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(required = false) Double minPrice,
                                            @RequestParam(required = false) Double maxPrice,
                                            @RequestParam(required = false) String size,
                                            @RequestParam(defaultValue = "1") int page,
                                            @RequestParam(required = false) String keyword) {
        ResponseData responseData = new ResponseData();
        Page<ProductDTO> data = productService.getAllProducts(brand, category, sort, minPrice, maxPrice, size, page, keyword);
        responseData.setData(data.getContent());
        responseData.setTotalPages(data.getTotalPages());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    @GetMapping("/details/{id}")
    public ResponseEntity<?> getProductDetailById(@PathVariable int id) {
        ResponseData responseData = new ResponseData();
        responseData.setData(productService.getProductDetailsById(id));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @GetMapping("/file/{filename:.+}")
    public ResponseEntity<?> getImagesProducts(@PathVariable String filename) {
        Resource resource = fileService.loadFile(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<?> getAllProductsForAdmin(@RequestParam(required = false, defaultValue = "0") int start,
                                                    @RequestParam(required = false, defaultValue = "5") int length,
                                                    @RequestParam(value = "search[value]", required = false, defaultValue = "") String searchValue) {
        ResponseData responseData = new ResponseData();
        int page = start / length;
        PageRequest pageRequest = PageRequest.of(page, length);
        responseData.setData(productService.getAllProductsForAdmin(pageRequest, searchValue));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/details")
    public ResponseEntity<?> saveDetail(@RequestBody DetailRequest detailRequest) {
        try {
            DetailDTO addedDetail = productDetailService.save(detailRequest);
            ResponseData responseData = new ResponseData();
            responseData.setData(addedDetail);
            responseData.setDescription("successfully");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/details/{id}")
    public ResponseEntity<?> deleteProductDetail(@PathVariable Integer id) {
        ResponseData responseData = new ResponseData();
        try {
            productDetailService.delete(id);
            responseData.setDescription("Delete successfully");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<?> saveProduct(@Valid @RequestBody ProductRequest productRequest) {
        try {
            ProductDTO productDTO = productService.saveProduct(productRequest);
            ResponseData responseData = new ResponseData();
            responseData.setData(productDTO);
            responseData.setDescription("successfully");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/images/{id}/{oldfile}")
    public ResponseEntity<?> updateImages(@PathVariable Integer id, @PathVariable String oldfile, @RequestPart MultipartFile filename) {
        if (imageService.updateProductImage(id, oldfile, filename)) {
            return ResponseEntity.ok().body("Image uploaded successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not upload the file");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/images/{id}")
    public ResponseEntity<?> addImages(@PathVariable Integer id, @RequestPart MultipartFile filename) {
        if (imageService.addProductImage(id, filename)) {
            return ResponseEntity.ok().body("Image added successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not upload the file");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}/images/{filename:.+}")
    public ResponseEntity<?> deleteImage(@PathVariable Integer id, @PathVariable String filename) {
        ResponseData responseData = new ResponseData();
        try {
            imageService.delete(id, filename);
            responseData.setDescription("Delete successfully");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 0), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        ResponseData responseData = new ResponseData();
        try {
            productService.delete(id);
            responseData.setDescription("Delete successfully");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new ResponseData(false, e.getMessage(), null, 0), HttpStatus.BAD_REQUEST);
        }
    }
}
