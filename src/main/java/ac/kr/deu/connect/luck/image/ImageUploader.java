package ac.kr.deu.connect.luck.image;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Component
@Slf4j
public class ImageUploader {

    private final String BASE_URL = "https://api.imgbb.com/1/upload";
    private final Long EXPIRATION = 15552000L;
    private final RestTemplate restTemplate = new RestTemplate();
    @Value("${api-key.imgbb}")
    private String API_KEY;

    public ImageUploadResponse uploadImage(MultipartFile file) {
        try {
            byte[] image = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(image);

            String apiUrl = String.format("%s?key=%s&expiration=%d", BASE_URL, API_KEY, EXPIRATION);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<>();
            bodyMap.add("image", base64Image);

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(bodyMap, headers);

            ResponseEntity<ImageUploadResponse> response = restTemplate.postForEntity(apiUrl, request, ImageUploadResponse.class);

            log.info("Image upload response: {}", response.getBody());
            log.info("Image upload URL: {}", response.getBody().getData().getUrl());
            return response.getBody();
        } catch (Exception e) {
            log.error("Image upload failed: {}", e.getMessage());
            ImageUploadResponse response = new ImageUploadResponse();
            response.setData(new ImageUploadResponse.Data());
            response.getData().setUrl("https://picsum.photos/1600/900");
            return response;
        }
    }
}
