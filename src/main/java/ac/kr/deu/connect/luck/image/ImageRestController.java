package ac.kr.deu.connect.luck.image;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Tag(name = "09-Image", description = "Image API")
@RequestMapping("/api/image")
@AllArgsConstructor
public class ImageRestController {

    private final ImageUploader imageUploader;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ImageUrlResponse uploadImage(
            @Parameter(description = "multipart/form-data 형식의 이미지 리스트를 input으로 받습니다. 이때 key 값은 image 입니다.")
            @RequestPart("image") MultipartFile multipartFile) {
        return new ImageUrlResponse(imageUploader.uploadImage(multipartFile).getData().getUrl());
    }

}
