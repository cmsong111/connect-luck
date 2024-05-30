package ac.kr.deu.connect.luck.ad

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "10-Advertisement API", description = "광고 관련 API")
@RestController
@RequestMapping("/api/ad")
class AdRestController {

    val adList: List<String> = listOf(
        "https://i.ibb.co/tXHJ42V/Lime-Wire-AI-Studio-Asset-7.jpg",
        "https://i.ibb.co/MphYSMG/Lime-Wire-AI-Studio-Asset-6.jpg",
        "https://i.ibb.co/8X33yyG/Lime-Wire-AI-Studio-Asset-5.jpg",
        "https://i.ibb.co/gvmGnq1/Lime-Wire-AI-Studio-Asset-4.jpg",
        "https://i.ibb.co/9Zt0868/Lime-Wire-AI-Studio-Asset-3.jpg",
        "https://i.ibb.co/m6fwTnX/Lime-Wire-AI-Studio-Asset-2.jpg",
        "https://i.ibb.co/c1mr03H/Lime-Wire-AI-Studio-Asset-1.jpg",
        "https://i.ibb.co/Q99zZj0/Lime-Wire-AI-Studio-Asset.jpg"
    )

    @GetMapping
    fun getAd(): List<String> {
        return adList;
    }
}
