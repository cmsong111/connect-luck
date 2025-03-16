package ac.kr.deu.connect.luck.event.service

import ac.kr.deu.connect.luck.common.storage.StorageService
import ac.kr.deu.connect.luck.event.entity.EventStatus
import ac.kr.deu.connect.luck.event.repository.EventRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventAdminService(
    private val eventRepository: EventRepository,
    private val storageService: StorageService,
) {
    /**
     * 행사 등록 메소드
     */
    @Transactional
    fun createEvent() {
        TODO("Not yet implemented")
    }

    /**
     * 행사 통합 수정 메소드
     */
    @Transactional
    fun updateEvent(
        eventId: Long,
        status: EventStatus? = null,
    ) {
        val event = eventRepository.findByIdOrNull(eventId)
            ?: throw IllegalArgumentException("Event not found")

        // 이벤트 상태 변경
        status?.let { event.status = it }

        TODO("Not yet implemented")
    }

    /**
     * 행사 삭제 메소드
     */
    @Transactional
    fun deleteEvent(
        eventId: Long,
    ) {
        eventRepository.deleteById(eventId)
    }
}
