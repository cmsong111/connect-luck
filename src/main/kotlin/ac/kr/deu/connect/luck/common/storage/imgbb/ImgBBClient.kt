package ac.kr.deu.connect.luck.common.storage.imgbb

import ac.kr.deu.connect.luck.common.storage.StorageService
import io.github.oshai.kotlinlogging.KotlinLogging
import java.util.Base64
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Profile
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.multipart.MultipartFile

@Profile("!test")
@Component
@EnableConfigurationProperties(ImgBBProperties::class)
class ImgBBClient(
    private val properties: ImgBBProperties
) : StorageService {
    private val restClient: RestClient = RestClient.create()

    override fun save(file: MultipartFile): String {
        return uploadImage(file)
    }

    override fun save(vararg files: MultipartFile): List<String> {
        return files.map { uploadImage(it) }
    }

    private fun uploadImage(image: MultipartFile): String {
        // Multipart/form-data 형식으로 이미지를 Base64로 인코딩하여 전송
        val parts: MultiValueMap<String, Any> = LinkedMultiValueMap()
        parts.add("image", Base64.getEncoder().encodeToString(image.bytes))


        val response: Map<*, *>? = restClient.post()
            .uri("${properties.url}?key=${properties.key}")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(parts)
            .retrieve()
            .body(Map::class.java)

        // 업로드 결과에서 이미지 URL 추출
        val data = response?.get("data") as Map<*, *>?
        return data?.get("url").toString()
    }

    init {
        logger.info { "ImgBBClient::init" }
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}
