package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.auth.SignUpRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public String gerUserInfo(Principal principal, Model model) {
        User user = userService.findUserByEmail(principal.getName());
        model.addAttribute("userInfo", userService.findUserInfo(user.getId()));
        return "user/profile";
    }

    @GetMapping("/delete")
    public String deleteUserPageRequest() {
        return "user/delete";
    }

    @DeleteMapping("/delete")
    public String deleteUser(Principal principal) {
        userService.deleteUser(principal.getName());
        return "redirect:/auth/logout";
    }

    @GetMapping("/update")
    public String updateUserPageRequest(Model model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        model.addAttribute("userInfo", userService.findUserInfo(user.getId()));
        return "user/profile_edit";
    }

    @PostMapping("/update")
    public String updateUser(
            Principal principal,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("phoneNumber") String phoneNumber
    ) {
        SignUpRequest signUpRequest = new SignUpRequest(principal.getName(), password, name, phoneNumber);
        userService.updateUser(principal.getName(), signUpRequest);
        return "redirect:/user";
    }


}
