package ac.kr.deu.connect.luck.auth.dto

data class EmailResponse(
    val email: String
) {
    override fun toString(): String {
        return "EmailResponse(email='$email')"
    }
}
