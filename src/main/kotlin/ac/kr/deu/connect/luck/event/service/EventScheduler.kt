package ac.kr.deu.connect.luck.event.service

import ac.kr.deu.connect.luck.event.entity.Event
import ac.kr.deu.connect.luck.event.repository.EventRemoteRepository
import ac.kr.deu.connect.luck.event.repository.EventRepository
import ac.kr.deu.connect.luck.event.repository.EventSource
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventScheduler(
    private val eventRemoteRepositories: List<EventRemoteRepository>,
    private val eventRepository: EventRepository,
) {
    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    fun syncEventsFromRemote() {
        logger.info("Starting sync events from remote")
        val eventSources: List<EventSource> = eventRemoteRepositories.flatMap { it.getEventSource() }

        eventSources.forEach { eventSource ->
            val event: Event? = eventRepository.findByProvider(eventSource.provider)

            if (event != null) {
                event.title = eventSource.title
                event.thumbnail = eventSource.thumbnail
                event.content = eventSource.content
                eventRepository.save(event)
            } else {
                eventRepository.save(
                    Event(
                        provider = eventSource.provider,
                        title = eventSource.title,
                        content = eventSource.content,
                        thumbnail = eventSource.thumbnail,
                        address = eventSource.address,
                        detailAddress = eventSource.detailAddress,
                        status = eventSource.status,
                        trafficInfo = eventSource.trafficInfo,
                        managerId = 3L,
                    )
                )
            }
        }
    }

    init{
        syncEventsFromRemote()
    }

    companion object {
        val logger = KotlinLogging.logger {}
    }
}
