package ac.kr.deu.connect.luck.banner

import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.BANNER
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = BANNER, description = "Banner API")
@RestController
@RequestMapping("/api/v1/banner")
class BannerRestController {

    val adList: List<String> = listOf(
        "https://picsum.photos/id/501/1800/600",
        "https://picsum.photos/id/502/1800/600",
        "https://picsum.photos/id/503/1800/600",
        "https://picsum.photos/id/504/1800/600",
    )

    @GetMapping
    fun getAd(): List<String> {
        return adList;
    }
}
