package next.finalproject.t03.imagecomparison.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

// criando classe para armazenar os parametros de retorno da imagem mais similar encontrada no banco

public class MostSimilarImageResponse implements Serializable {

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("image")
    private byte[] image;

}
