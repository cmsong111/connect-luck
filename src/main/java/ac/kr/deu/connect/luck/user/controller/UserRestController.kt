package ac.kr.deu.connect.luck.user.controller

import ac.kr.deu.connect.luck.common.controller.data.AuthenticatedUser
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.BEARER_AUTH
import ac.kr.deu.connect.luck.configuration.SpringDocConfig.Companion.USER
import ac.kr.deu.connect.luck.user.controller.request.UserUpdateForm
import ac.kr.deu.connect.luck.user.controller.response.UserDetailResponse
import ac.kr.deu.connect.luck.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user")
@SecurityRequirement(name = BEARER_AUTH)
@Tag(name = USER, description = "사용자 관련 API")
class UserRestController(
    private val userService: UserService
) {

    @DeleteMapping
    @Operation(
        summary = "사용자 삭제",
        description = """<h1>사용자 정보를 삭제합니다.</h1>
        푸드트럭 매니저의 경우 푸드트럭 정보또한(후기 포함) 삭제됩니다.
        """,
    )
    fun deleteUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser
    ): ResponseEntity<Void> {
        userService.withdraw(authenticatedUser.email)
        return ResponseEntity.noContent().build()
    }

    @GetMapping
    @Operation(
        summary = "사용자 조회",
        description = """<h1>JWT 토큰을 기반으로 로그인 된 유저 정보를 반환합니다.</h1>
        헤더에 Authorization 키에 Bearer 토큰을 넣어주세요.
        """,
    )
    fun getUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser
    ): ResponseEntity<UserDetailResponse> {
        println("authenticatedUser.email = ${authenticatedUser.email}")
        return ResponseEntity.ok(userService.getUserByEmail(authenticatedUser.email))
    }

    @PatchMapping
    @Operation(
        summary = "사용자 정보 수정",
        description = """<h1>사용자정보를 수정합니다.</h1>
            변경하지 않을 정보는 null로 입력하세요.<br><br>
            <h3>주의: 사용자 정보 변경 시 JWT 토큰을 다시 발급 받으세요.</h3>
            이전 토큰을 사용하면 요청이 정상적으로 처리되지 않습니다.
            <h3>roles에 역할을 담아 보내면 역할이 추가됩니다.</h3>
            <p>권한 수거는 관리자만 가능합니다.</p>
            """
    )
    fun updateUser(
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
        @RequestBody userUpdateForm: UserUpdateForm
    ): ResponseEntity<UserDetailResponse> {
        return ResponseEntity.ok(
            userService.updateUserInfo(
                authenticatedUser.email,
                userUpdateForm
            )
        )
    }
}
