package ac.kr.deu.connect.luck.image;

import ac.kr.deu.connect.luck.image.ImageUploader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ImageUploaderTest {

    @Autowired
    private ImageUploader imageUploader;

    @Test
    @DisplayName("이미지 업로드 테스트")
    void uploadImage() throws Exception {
        // Given
        final String filePath = "src/test/resources/test.jpg";
        FileInputStream fis = new FileInputStream(filePath);
        MockMultipartFile file = new MockMultipartFile("file", fis);

        // When
        ImageUploadResponse imageUrl = imageUploader.uploadImage(file);

        // Then
        assertEquals(200, imageUrl.getStatus());
        assertNotNull(imageUrl.getData().getUrl());
    }

}
