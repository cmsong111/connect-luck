package ac.kr.deu.connect.luck.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavController {

    @GetMapping("/about-us")
    public String aboutUs() {
        return "about_us";
    }
}
