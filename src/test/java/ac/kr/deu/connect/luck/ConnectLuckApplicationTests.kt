package ac.kr.deu.connect.luck

import ac.kr.deu.connect.luck.mocks.MockStorageService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@Import(MockStorageService::class)
class ConnectLuckApplicationTests {

    @Test
    fun contextLoads() {
    }

}
