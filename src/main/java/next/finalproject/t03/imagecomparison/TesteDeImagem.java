package next.finalproject.t03.imagecomparison;

import java.io.File;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.AverageHash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import dev.brachtendorf.jimagehash.matcher.exotic.SingleImageMatcher;


public class TesteDeImagem {

	public static void main(String[] args) throws IOException {
		
		File img0 = new File("C:/Users/SuperUsuário/Pictures/Vitor_Cavalcanti.png");
		File img1 = new File("C:/Users/SuperUsuário/Pictures/teste1.png");

		System.out.println("Carregou o arquivo");

		HashingAlgorithm hasher = new PerceptiveHash(32);

		Hash hash0 = hasher.hash(img0);
		Hash hash1 = hasher.hash(img1);

		System.out.println("Fez o hash! ");

		double similarityScore = hash0.normalizedHammingDistance(hash1);

		if(similarityScore < .2) {
			System.out.println("É igual! ");
		}
		else{
			System.out.println("é diferente! ");
		}

		/* //Chaining multiple matcher for single image comparison

		SingleImageMatcher matcher = new SingleImageMatcher();
		matcher.addHashingAlgorithm(new AverageHash(64),.3);
		matcher.addHashingAlgorithm(new PerceptiveHash(32),.2);

		if(matcher.checkSimilarity(img0,img1)) {
    		//Considered a duplicate in this particular case
		} */
	}


}