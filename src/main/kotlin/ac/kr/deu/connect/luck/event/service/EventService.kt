package ac.kr.deu.connect.luck.event.service

import ac.kr.deu.connect.luck.event.entity.Event
import ac.kr.deu.connect.luck.event.entity.EventStatus
import ac.kr.deu.connect.luck.event.repository.EventRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventService(
    private val eventRepository: EventRepository,
) {
    /**
     * 행사 종합 조회 메소드
     * @param keyword 검색 키워드 (행사명, 행사 설명)
     * @param pageable 페이징 정보
     * @return 행사 목록
     */
    @Transactional(readOnly = true)
    fun getEvents(
        keyword: String? = null,
        managerId: Long? = null,
        status: EventStatus? = null,
        pageable: Pageable,
    ): Page<Event> {
        return eventRepository.findAll(pageable)
    }

    /**
     * 행사 주최자별 행사 목록 조회 메소드
     * @param managerId 주최자 ID
     * @param pageable 페이징 정보
     * @return 행사 목록
     */
    @Transactional(readOnly = true)
    fun getEventsByManagerId(
        managerId: Long,
        pageable: Pageable,
    ): Page<Event> {
        return eventRepository.findByManagerId(managerId, pageable)
    }

    /**
     * 행사 상세 조회 메소드
     * @param eventId 행사 ID
     * @return 행사 정보
     */
    @Transactional(readOnly = true)
    fun getEvent(eventId: Long): Event {
        return eventRepository.findByIdOrNull(eventId)
            ?: throw IllegalArgumentException("Event not found")
    }
}
