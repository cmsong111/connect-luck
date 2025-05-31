package ac.kr.deu.connect.luck.recruitment.repository

import ac.kr.deu.connect.luck.recruitment.entity.FoodTruckRecruitment
import ac.kr.deu.connect.luck.recruitment.entity.RecruitmentStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FoodTruckRecruitmentRepository : JpaRepository<FoodTruckRecruitment, Long> {

    /**
     * 상태에 따라 푸드트럭 모집 정보를 조회합니다.
     */
    fun findByStatus(
        status: RecruitmentStatus,
        pageable: Pageable,
    ): Page<FoodTruckRecruitment>
}
