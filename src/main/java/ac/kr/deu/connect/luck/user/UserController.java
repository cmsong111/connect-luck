package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.auth.SignUpRequest;
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
        model.addAttribute("userInfo", userService.findUserInfo(principal.getName()));
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
    public String updateUserPageRequest(
            Model model, Principal principal) {
        model.addAttribute("userInfo", userService.findUserInfo(principal.getName()));
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

    @GetMapping("/add-role")
    public String addRolePageRequest() {
        return "user/add_role";
    }

    @PostMapping("/add-role")
    public String addRole(
            Principal principal,
            @RequestParam("role") UserRole role
    ) {
        userService.setUserRole(principal.getName(), role);
        return "redirect:/user";
    }


}
