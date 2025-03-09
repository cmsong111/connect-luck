package ac.kr.deu.connect.luck.common.storage

import org.springframework.web.multipart.MultipartFile

interface StorageService {
    fun save(file: MultipartFile) : String
    fun save(vararg files: MultipartFile) : List<String>
}
