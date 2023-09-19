package next.finalproject.t03.imagecomparison.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dev.brachtendorf.jimagehash.hash.Hash;
import dev.brachtendorf.jimagehash.hashAlgorithms.HashingAlgorithm;
import dev.brachtendorf.jimagehash.hashAlgorithms.PerceptiveHash;
import next.finalproject.t03.imagecomparison.entity.ImageData;
import next.finalproject.t03.imagecomparison.repository.ImageDataRepository;
import next.finalproject.t03.imagecomparison.util.ImageUtils;

@Service
public class ImageService {

    @Autowired
    private ImageDataRepository repository;

    public String uploadImage(MultipartFile file) throws IOException {

        // verificando se ja existe imagem no banco de dados com o nome passado como
        // parametro no request
        Optional<ImageData> imagemNoBanco = repository.findByName(file.getOriginalFilename());

        // caso nenhuma imagem com o nome passado exista, vamos inserir
        if (!imagemNoBanco.isPresent()) {

            // monta o objeto ImageDate e insere no banco de dados
            ImageData imageData = repository.save(criaObjetoImagemBancoDados(file));

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

    public byte[] getMostSimilarImage(MultipartFile file) throws IOException {

        //Recupera todas as imagens do banco de dados
        List<ImageData> todasAsImagens = this.findAll();

        //Cria objeto ImageData da imagem recebida na requisicao
        ImageData imagemParaComparar = criaObjetoImagemBancoDados(file);

        //Recupera o hash da imagem recebida na requisicao
        Hash hashParaComparar = imagemParaComparar.getImageHash();

        //variavel com o valor maximo de similaridade dos hashs
        double menorSimilaridade = 1;
        //variavel que vai guardar a imagem mais similar
        ImageData imagemMaisSimilar = new ImageData();

        // percorrendo a lista e comparando as imagens
        for (ImageData imagemAtualDaLista : todasAsImagens) {

            //recupera o Hash da imagem atual da lista
            Hash currentHash = imagemAtualDaLista.getImageHash();

            //calcula a simularidade entre a imagem atual e a imagem recebida na requisição
            double similaridadeAtual = hashParaComparar.normalizedHammingDistance(currentHash);

            //verifica se a imagem atual é mais simular do que a referencia anterior
            //caso veridadeiro, atualiza a referencia da imagem mais parecida
            // e atualiza o menor valor de similaridade(quanto menor mais parecido)
            if (similaridadeAtual < menorSimilaridade) {

                menorSimilaridade = similaridadeAtual;
                imagemMaisSimilar = imagemAtualDaLista;
            }

        }

        //retorna os dados da imagem mais similar decomprimido
        return ImageUtils.decompressImage(imagemMaisSimilar.getImageData());
    }

    private ImageData criaObjetoImagemBancoDados(MultipartFile file) throws IOException {

        HashingAlgorithm hasher = new PerceptiveHash(32);

        return ImageData.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtils.compressImage(file.getBytes()))
                .imageHash(hasher.hash(convertMultiPartToFile(file))).build();
    }

    public List<ImageData> findAll() {
        return repository.findAll();
    }

    public File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

}
