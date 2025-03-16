package ac.kr.deu.connect.luck.user.controller

import ac.kr.deu.connect.luck.common.controller.data.AuthenticatedUser
import ac.kr.deu.connect.luck.user.controller.request.UserUpdateForm
import ac.kr.deu.connect.luck.user.entity.UserRole
import ac.kr.deu.connect.luck.user.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/user")
class UserController(
    private val userService: UserService,
) {
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun getUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        model: Model,
    ): String {
        model.addAttribute(
            "userInfo",
            userService.getUserByEmail(authenticatedUser.email)
        )
        return "user/profile"
    }

    @GetMapping("/withdrawal")
    @PreAuthorize("isAuthenticated()")
    fun deleteUserPageRequest(): String = "user/delete"


    @PostMapping("/withdrawal")
    @PreAuthorize("isAuthenticated()")
    fun deleteUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): String {
        userService.withdraw(authenticatedUser.email)
        return "redirect:/logout"
    }

    @GetMapping("/update")
    @PreAuthorize("isAuthenticated()")
    fun updatePasswordPageRequest(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        model: Model,
    ): String {
        model.addAttribute(
            "userUpdateForm",
            UserUpdateForm(
                name = userService.getUserByEmail(authenticatedUser.email).name,
                userService.getUserByEmail(authenticatedUser.email).phone
            )
        )
        return "user/profile_edit"
    }

    @PostMapping("/update")
    @PreAuthorize("isAuthenticated()")
    fun updatePassword(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        userUpdateForm: UserUpdateForm,
    ): String {
        userService.updateUserInfo(authenticatedUser.email, userUpdateForm)
        return "redirect:/user"
    }

    @GetMapping("/role")
    fun addRolePageRequest(): String = "user/add_role"

    @PostMapping("/role")
    fun addRole(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        role: UserRole,
    ): String {
        userService.addRole(
            authenticatedUser.email,
            role
        )
        return "redirect:/user"
    }


}
