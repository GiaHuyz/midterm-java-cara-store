package com.ecommerce.cara.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public boolean saveFile(MultipartFile file);
    public Resource loadFile(String filename);
}
