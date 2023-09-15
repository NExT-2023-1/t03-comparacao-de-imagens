package next.finalproject.t03.imagecomparison.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import next.finalproject.t03.imagecomparison.entity.ImageData;
import next.finalproject.t03.imagecomparison.repository.ImageDataRepository;
import next.finalproject.t03.imagecomparison.util.ImageUtils;

@Service
public class ImageService {

    @Autowired
    private ImageDataRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {

       //verificando se ja existe imagem no banco de dados com o nome passado como parametro no request
        Optional<ImageData> imagemNoBanco = repository.findByName(file.getOriginalFilename());

        //caso nenhuma imagem com o nome passado exista, vamos inserir
        if (!imagemNoBanco.isPresent()) {

            //monta o objeto ImageDate e insere no banco de dados
            ImageData imageData = repository.save(ImageData.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtils.compressImage(file.getBytes())).build());

            if (imageData != null) {
                return "ARQUIVO CARREGADO COM SUCESSO! " + file.getOriginalFilename();
            } else {
                return "nenhum arquivo inserido";
            }

        } else {
            return "A imagem com o nome " + file.getOriginalFilename() + " ja existe no banco de dados.";
        }
    }

    public byte[] downloadImages(String fileName) {
        Optional<ImageData> dbImageData = repository.findByName(fileName);
        byte[] image = ImageUtils.decompressImage(dbImageData.get().getImageData());
        return image;

    }

    public boolean deleteImage(Long longId) {
        ImageData image = this.repository.findById(longId).orElse(null);
        if (image != null) {
            this.repository.deleteById(longId);
            return true;
        }
        return false;
    }
}
