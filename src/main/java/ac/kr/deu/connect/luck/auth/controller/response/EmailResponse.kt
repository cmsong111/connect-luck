package ac.kr.deu.connect.luck.auth.controller.response

data class EmailResponse(
    val email: String
) {
    override fun toString(): String {
        return "EmailResponse(email='$email')"
    }
}
