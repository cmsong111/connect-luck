package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.auth.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String gerUserInfo(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("userInfo", userService.findUserInfo(user.getId()));
        return "user/profile";
    }

    @GetMapping("/delete")
    public String deleteUserPageRequest() {
        return "user/delete";
    }

    @DeleteMapping("/delete")
    public String deleteUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        userService.deleteUser(user.getId());
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/update")
    public String updateUserPageRequest(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("userInfo", userService.findUserInfo(user.getId()));
        return "user/profile_edit";
    }

    @PostMapping("/update")
    public String updateUser(
            HttpServletRequest request,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber
    ) {
        User user = (User) request.getSession().getAttribute("user");
        SignUpRequest signUpRequest = new SignUpRequest(user.getEmail(), password, name, phoneNumber);
        userService.updateUser(user.getId(), signUpRequest);
        return "redirect:/user";
    }


}
