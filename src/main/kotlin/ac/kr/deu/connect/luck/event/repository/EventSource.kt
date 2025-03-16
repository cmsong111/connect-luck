package ac.kr.deu.connect.luck.event.repository

import ac.kr.deu.connect.luck.event.entity.EventProvider
import ac.kr.deu.connect.luck.event.entity.EventStatus

data class EventSource(
    val provider: EventProvider,
    val title: String,
    val thumbnail: String,
    val content: String,
    val status: EventStatus,
    val address: String,
    val detailAddress: String,
    val trafficInfo: String?,
) {
}
