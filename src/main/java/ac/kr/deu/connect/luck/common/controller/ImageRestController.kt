package ac.kr.deu.connect.luck.common.controller

import ac.kr.deu.connect.luck.common.storage.StorageService
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@Tag(name = "09-Image", description = "Image API")
@RequestMapping("/api/v2/image")
class ImageRestController(
    private val storageService: StorageService,
) {
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImage(
        @RequestPart("image") image: MultipartFile
    ): ImageUrlResponse {
        return ImageUrlResponse(
            imageUrl = storageService.save(image)
        )
    }

    @Schema(description = "Image 업로드 응답")
    data class ImageUrlResponse(
        @field:Schema(description = "이미지 URL", example = "https://example.com/image.jpg")
        val imageUrl: String
    )

}
