package com.exemplo.imagem.database.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exemplo.imagem.database.entity.ImageData;
import com.exemplo.imagem.database.repository.ImageDataRepository;
import com.exemplo.imagem.database.util.ImageUtils;

@Service
public class ImageService {

    @Autowired    
    private ImageDataRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {

        ImageData imageData = repository.save(ImageData.builder()
        .name(file.getOriginalFilename())
        .type(file.getContentType())
        .imageData(ImageUtils.compressImage(file.getBytes())).build());

        if (imageData != null) {
            return "File uploaded successfully!! " + file.getOriginalFilename();
        }
        return null;       
    }

    public byte[] downloadImages(String fileName) {
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] image = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return image; 

    }


    
}
