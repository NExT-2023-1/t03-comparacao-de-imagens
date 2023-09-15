package com.exemplo.imagem.database.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exemplo.imagem.database.entity.ImageData;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {

    Optional<ImageData> findByName(String fileName);

}