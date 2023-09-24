package next.finalproject.t03.imagecomparison.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CompareTwoImageResponse {

    private Map<String, String> responseMessage;
    private Boolean isValidImage;

}
