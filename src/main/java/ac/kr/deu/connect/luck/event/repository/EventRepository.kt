package ac.kr.deu.connect.luck.event.repository

import ac.kr.deu.connect.luck.event.entity.Event
import ac.kr.deu.connect.luck.event.entity.EventProvider
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EventRepository : JpaRepository<Event, Long> {
    fun findByManagerId(
        managerId: Long,
        pageable: Pageable,
    ): Page<Event>


    fun findByProvider(provider: EventProvider): Event?
}
