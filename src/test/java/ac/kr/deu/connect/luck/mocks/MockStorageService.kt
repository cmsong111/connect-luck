package ac.kr.deu.connect.luck.mocks

import ac.kr.deu.connect.luck.common.storage.StorageService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.test.context.TestComponent
import org.springframework.web.multipart.MultipartFile

@TestComponent
class MockStorageService : StorageService {
    override fun save(file: MultipartFile): String {
        return "mock"
    }

    override fun save(vararg files: MultipartFile): List<String> {
        return files.map { "mock" }
    }

    init {
        logger.info { "MockStorageService::init" }
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}
