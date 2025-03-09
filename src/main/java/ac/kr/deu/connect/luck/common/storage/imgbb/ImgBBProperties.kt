package ac.kr.deu.connect.luck.common.storage.imgbb

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "imgbb")
data class ImgBBProperties(
    val url: String = "https://api.imgbb.com/1/upload",
    val key: String,
)
