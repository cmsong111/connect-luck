package ac.kr.deu.connect.luck.foodtruck.service

import ac.kr.deu.connect.luck.common.exception.NotFoundException
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckCategory
import ac.kr.deu.connect.luck.foodtruck.repository.FoodTruckRepository
import ac.kr.deu.connect.luck.foodtruck.service.data.FoodTruckData
import ac.kr.deu.connect.luck.foodtruck.service.data.FoodTruckSummaryData
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.data.util.TypeUtils.type
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FoodTruckService(
    private val foodTruckRepository: FoodTruckRepository,
) {
    /**
     * 푸드트럭 목록을 조회합니다.
     * @param name 푸드트럭 이름
     * @param type 푸드트럭 종류
     * @param pageable 페이징 정보
     * @return 푸드트럭 목록
     */
    @Transactional(readOnly = true)
    fun getFoodTrucks(
        category: FoodTruckCategory? = null,
        managerId: Long? = null,
        pageable: Pageable
    ): Page<FoodTruckSummaryData> {
        return foodTruckRepository.findFoodTrucks(
            category = category,
            managerId = managerId,
            pageable = pageable
        ).map {
            FoodTruckSummaryData.from(it)
        }
    }

    /**
     * 푸드트럭을 조회합니다.
     * @param id 푸드트럭 식별자
     * @return 푸드트럭
     * @throws NotFoundException 푸드트럭을 찾을 수 없는 경우
     */
    @Transactional(readOnly = true)
    fun getFoodTruck(id: Long): FoodTruckData {
        return foodTruckRepository.findByIdOrNull(id)?.let {
            FoodTruckData.from(it)
        } ?: throw NotFoundException("FoodTruck not found", "푸드트럭을 찾을 수 없습니다.")
    }
}
