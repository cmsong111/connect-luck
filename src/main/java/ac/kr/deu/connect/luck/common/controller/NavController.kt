package ac.kr.deu.connect.luck.common.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class NavController {
    @GetMapping("/about-us")
    fun about(): String = "about_us"
}
