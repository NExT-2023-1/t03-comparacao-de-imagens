package next.finalproject.t03.imagecomparison.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import next.finalproject.t03.imagecomparison.dto.MostSimilarImageResponse;
import next.finalproject.t03.imagecomparison.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private ImageService service;

	// consertando o erro de cors que a aplicação estava dando
	// pelo que eu entendi o cors regular quem acessa o backend
	@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
	@PostMapping
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
		String uploadImage = service.uploadImage(file);
		return ResponseEntity.status(HttpStatus.OK).body(uploadImage);
	}

	@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
	@GetMapping("/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
		byte[] imageData = service.downloadImages(fileName);
		return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/jpeg")).body(imageData);
	}

	@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
	@DeleteMapping("/{longId}")
	public ResponseEntity<?> deleteImage(@PathVariable Long longId) {
		if (service.deleteImage(longId)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@CrossOrigin(origins= {"*"}, maxAge = 4800, allowCredentials = "false")
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

}
