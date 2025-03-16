package ac.kr.deu.connect.luck.event.repository

interface EventRemoteRepository {
    fun getEventSource(): List<EventSource>
}
