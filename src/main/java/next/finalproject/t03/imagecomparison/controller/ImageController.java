package next.finalproject.t03.imagecomparison.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import next.finalproject.t03.imagecomparison.dto.MostSimilarImageResponse;
import next.finalproject.t03.imagecomparison.entity.ImageData;
import next.finalproject.t03.imagecomparison.service.ImageService;
import next.finalproject.t03.imagecomparison.util.ImageUtils;

@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private ImageService service;

	@PostMapping
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
		String uploadImage = service.uploadImage(file);
		return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
	}

	@GetMapping("/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
		byte[] imageData = service.downloadImages(fileName);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpeg")).body(imageData);
	}

	@DeleteMapping("/{longId}")
	public ResponseEntity<?> deleteImage(@PathVariable Long longId) {
		if (service.deleteImage(longId)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping(value= "/getMostSimilar")
	public ResponseEntity<?> getMostSimilarImage(@RequestParam("image") MultipartFile file) {

		try {
			MostSimilarImageResponse imageData = service.getMostSimilarImage(file);

			if(imageData.getImage() != null) {

			return ResponseEntity.status(HttpStatus.OK)
					.contentType(MediaType.valueOf("image/jpeg"))
					.body(imageData.getImage());
					
			}else{
				return ResponseEntity.status(HttpStatus.OK).body(imageData.getResponseMessage());
			}

		} catch (IOException ex) {

			System.out.println("Erro de entrada e saida nos arquivos");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

	@PostMapping(value= "/comapretwoimages")
	public ResponseEntity<?> getSimilarity(@RequestParam("image1") MultipartFile file1, @RequestParam("image2") MultipartFile file2) throws IOException {

		HashingAlgorithm hasher = new PerceptiveHash(32);		

		File firsImage = convertMultiPartToFile(file1);
		File secondImage = convertMultiPartToFile(file2);

		Hash hash0 = hasher.hash(firsImage);
		Hash hash1 = hasher.hash(secondImage);

		double similarityScore = hash0.normalizedHammingDistance(hash1);

		System.out.println("Score de similaridade = " + similarityScore);

		if(similarityScore < .2) {
			return ResponseEntity.status(HttpStatus.OK).body("É igual! ");
		}
		else{
			return ResponseEntity.status(HttpStatus.OK).body("é diferente! ");
		}
	}

	//Codigo repetido para poder rodar, não foi possivel importar o metodo de ImageService. Em analise!
	public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
