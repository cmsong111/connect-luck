package ac.kr.deu.connect.luck.now

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/now")
class NowController(
    val nowService: NowService
) {

    @Value("\${api-key.kakao}")
    private lateinit var apiKey: String

    @GetMapping
    fun now(model: Model): String {
        model.addAttribute("apiKey", apiKey)
        model.addAttribute("nowTruckList", nowService.getNow(null, null))
        return "now/now-map"
    }

}
