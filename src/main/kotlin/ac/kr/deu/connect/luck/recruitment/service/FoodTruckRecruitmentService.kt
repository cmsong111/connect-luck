package ac.kr.deu.connect.luck.recruitment.service

import ac.kr.deu.connect.luck.common.exception.NotFoundException
import ac.kr.deu.connect.luck.recruitment.entity.FoodTruckApplication
import ac.kr.deu.connect.luck.recruitment.entity.FoodTruckRecruitment
import ac.kr.deu.connect.luck.recruitment.entity.RecruitmentStatus
import ac.kr.deu.connect.luck.recruitment.repository.FoodTruckApplicationRepository
import ac.kr.deu.connect.luck.recruitment.repository.FoodTruckRecruitmentRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FoodTruckRecruitmentService(
    private val foodTruckRecruitmentRepository: FoodTruckRecruitmentRepository,
    private val foodTruckApplicationRepository: FoodTruckApplicationRepository,
) {

    @Transactional(readOnly = true)
    fun getRecruitmentList(
        page: Int,
        size: Int,
    ): Page<FoodTruckRecruitment> {
        return foodTruckRecruitmentRepository.findByStatus(
            status = RecruitmentStatus.RECRUITING,
            pageable = PageRequest.of(
                page,
                size,
            ),
        )
    }

    @Transactional(readOnly = true)
    fun getRecruitment(recruitmentId: Long): FoodTruckRecruitment {
        return foodTruckRecruitmentRepository.findByIdOrNull(recruitmentId)
            ?: throw NotFoundException(FoodTruckRecruitment::class.java, mapOf("recruitmentId" to recruitmentId))
    }

    @Transactional(readOnly = true)
    fun getRecruitmentApplications(recruitmentId: Long): List<FoodTruckApplication> {
        return foodTruckApplicationRepository.findByRecruitmentId(recruitmentId)
    }

    @Transactional(readOnly = true)
    fun countRecruitmentApplications(recruitmentId: Long): Long {
        return foodTruckApplicationRepository.countByRecruitmentId(recruitmentId)
    }
}
