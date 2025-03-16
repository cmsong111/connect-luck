package ac.kr.deu.connect.luck.event.repository.remote

import ac.kr.deu.connect.luck.event.entity.EventProvider
import ac.kr.deu.connect.luck.event.entity.EventStatus
import ac.kr.deu.connect.luck.event.entity.Provider
import ac.kr.deu.connect.luck.event.repository.EventRemoteRepository
import ac.kr.deu.connect.luck.event.repository.EventSource
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.util.DefaultUriBuilderFactory

@Component
class BusanFestivalRepository(
    @Value("\${data.go.kr.key}") private val key: String,
    private val objectMapper: ObjectMapper,
) : EventRemoteRepository {

    val restClient = RestClient.builder()
        .uriBuilderFactory(
            DefaultUriBuilderFactory("https://apis.data.go.kr").apply {
                encodingMode = DefaultUriBuilderFactory.EncodingMode.NONE
            }
        )
        .build()

    override fun getEventSource(): List<EventSource> {
        // 부산광역시_부산축제정보 서비스(공공 데이터 포털) API 호출
        val result: String? = restClient.get()
            .uri { uriBuilder ->
                uriBuilder.path("/6260000/FestivalService/getFestivalKr")
                    .queryParam("serviceKey", key)
                    .queryParam("pageNo", 1)
                    .queryParam("numOfRows", 100)
                    .queryParam("resultType", "json")
                    .build()
            }
            .retrieve()
            .body(String::class.java)

        // result를 Map으로 변환하여 EventSource로 변환
        val rootNode: JsonNode = objectMapper.readTree(result)
        logger.info { "Busan Festival Data Size: ${rootNode["getFestivalKr"]["totalCount"]}" }

        return rootNode["getFestivalKr"]["item"].map {
            EventSource(
                provider = EventProvider(Provider.BUSAN_FESTIVAL_API, it["UC_SEQ"].asText()),
                title = it["MAIN_PLACE"].asText(),
                thumbnail = it["MAIN_IMG_THUMB"].asText(),
                content = it["ITEMCNTNTS"].asText().trim(),
                status = EventStatus.BEFORE_APPLICATION,
                address = it["ADDR1"].asText(),
                detailAddress = it["ADDR2"].asText(),
                trafficInfo = it["TRFC_INFO"].asText(),
            )
        }
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}
