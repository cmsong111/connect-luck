package ac.kr.deu.connect.luck.foodtruck.service

import ac.kr.deu.connect.luck.common.storage.StorageService
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckCreateForm
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckMenuForm
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckUpdateForm
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruck
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckMenu
import ac.kr.deu.connect.luck.foodtruck.repository.FoodTruckRepository
import ac.kr.deu.connect.luck.foodtruck.service.data.FoodTruckData
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FoodTruckAdminService(
    private val storageService: StorageService,
    private val foodTruckRepository: FoodTruckRepository,
) {
    /**
     * 푸드트럭 신규 등록
     * @param managerId 매니저 식별자
     * @param foodTruckCreateForm 푸드트럭 등록 요청 DTO
     */
    @Transactional
    fun createFoodTruck(
        managerId: Long,
        foodTruckCreateForm: FoodTruckCreateForm
    ): FoodTruckData {
        val foodTruck = FoodTruck.create(
            name = foodTruckCreateForm.name,
            description = foodTruckCreateForm.description,
            thumbnail = storageService.save(foodTruckCreateForm.thumbnail),
            images = foodTruckCreateForm.images?.map { storageService.save(it) }?.toList() ?: emptyList(),
            type = foodTruckCreateForm.foodType,
            managerId = managerId
        )

        return FoodTruckData.from(
            foodTruckRepository.save(foodTruck)
        )
    }

    /**
     * 푸드트럭 정보 수정
     * @param foodTruckId 푸드트럭 식별자
     * @param foodTruckUpdateForm 푸드트럭 수정 요청 DTO
     */
    @Transactional
    fun updateFoodTruck(
        foodTruckId: Long,
        foodTruckUpdateForm: FoodTruckUpdateForm? = null,
        foodTruckMenus: List<FoodTruckMenuForm>? = null,
    ): FoodTruckData {
        val foodTruck: FoodTruck = foodTruckRepository.findByIdOrNull(foodTruckId)
            ?: throw IllegalArgumentException("FoodTruck not found")

        foodTruckUpdateForm?.let { foodTruckUpdateData ->
            foodTruck.update(
                name = foodTruckUpdateData.name,
                description = foodTruckUpdateData.description,
                thumbnail = foodTruckUpdateData.thumbnail?.let { storageService.save(it) },
                images = foodTruckUpdateData.images?.map { storageService.save(it) },
                type = foodTruckUpdateData.foodType
            )
        }

        foodTruckMenus?.let { menus ->
            val menuEntities = menus.map { menu ->
                FoodTruckMenu(
                    name = menu.name,
                    price = menu.price,
                    description = menu.description,
                    image = menu.image?.let { file -> storageService.save(file) }
                )
            }

            foodTruck.menus = menuEntities.toMutableList()
        }

        return FoodTruckData.from(foodTruck)
    }

    /**
     * 푸드트럭 메뉴 등록(수정)
     * <p>메뉴 수정 시 모든 메뉴를 다시 등록해야 함</p>
     * <p>FE에게서 모든 메뉴 데이터를 받아야 함</p>
     */
    @Transactional
    fun addFoodTruckMenu(
        foodTruckId: Long,
        menus: List<FoodTruckMenuForm>,
    ): FoodTruckData {
        val foodTruck = foodTruckRepository.findByIdOrNull(foodTruckId)
            ?: throw IllegalArgumentException("FoodTruck not found")

        val menuEntities = menus.map {
            FoodTruckMenu(
                name = it.name,
                price = it.price,
                description = it.description,
                image = it.image?.let { storageService.save(it) }
            )
        }

        foodTruck.menus = menuEntities.toMutableList()

        return FoodTruckData.from(foodTruck)
    }

    /**
     * 푸드트럭 삭제 (메뉴,리뷰 포함)
     */
    @Transactional
    fun deleteFoodTruck(foodTruckId: Long) {
        foodTruckRepository.deleteById(foodTruckId)
    }
}
