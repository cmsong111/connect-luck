package ac.kr.deu.connect.luck

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.web.multipart.MultipartFile

@EnableJpaAuditing
@SpringBootApplication
class ConnectLuckApplication

fun main(args: Array<String>) {
    runApplication<ConnectLuckApplication>(*args)
}
