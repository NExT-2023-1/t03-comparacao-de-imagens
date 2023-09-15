package next.finalproject.t03.imagecomparison.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import next.finalproject.t03.imagecomparison.entity.ImageData;

public interface ImageDataRepository extends JpaRepository<ImageData, Long> {

    Optional<ImageData> findByName(String fileName);

}