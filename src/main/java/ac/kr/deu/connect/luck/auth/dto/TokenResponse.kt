package ac.kr.deu.connect.luck.auth.dto

class TokenResponse(
    val token: String
) {
    override fun toString(): String {
        return "TokenResponse(token='$token')"
    }
}
