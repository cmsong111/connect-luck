package ac.kr.deu.connect.luck.image.dto

data class ImageUrlResponse(
    val imageUrl: String
) {
    override fun toString(): String {
        return "ImageUrlResponse(imageUrl='$imageUrl')"
    }
}
