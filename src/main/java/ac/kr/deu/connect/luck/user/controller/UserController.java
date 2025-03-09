package ac.kr.deu.connect.luck.user.controller;

import ac.kr.deu.connect.luck.auth.controller.request.SignUpRequest;
import ac.kr.deu.connect.luck.user.service.UserService;
import ac.kr.deu.connect.luck.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("isAuthenticated()")
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

    /**
     * 유저 정보 수정
     *
     * @param principal   로그인한 유저 정보
     * @param password    비밀번호
     * @param name        이름
     * @param phoneNumber 전화번호
     * @return 유저 정보 수정 후 프로필 페이지로 이동
     */
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
