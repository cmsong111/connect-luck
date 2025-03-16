package ac.kr.deu.connect.luck.common.exception

class CustomException(
     val customErrorCode: CustomErrorCode
) : RuntimeException(customErrorCode.message)
