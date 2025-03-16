package ac.kr.deu.connect.luck.event.application.repository

import ac.kr.deu.connect.luck.event.application.entity.EventApplication
import ac.kr.deu.connect.luck.event.application.entity.EventApplication.ApplicationId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventApplicationRepository : JpaRepository<EventApplication, ApplicationId> {
    /**
     * 행사별 신청 목록 조회 메소드
     * @param eventId 행사 ID
     * @param pageable 페이징 정보
     * @return 행사별 신청 목록
     */
    fun findByIdEventId(
        eventId: Long,
        pageable: Pageable,
    ): Page<EventApplication>

    /**
     * 푸드트럭별 신청 목록 조회 메소드
     * @param foodTruckId 푸드트럭 ID
     * @param pageable 페이징 정보
     * @return 푸드트럭별 신청 목록
     */
    fun findByIdFoodTruckId(
        foodTruckId: Long,
        pageable: Pageable,
    ): Page<EventApplication>
}
