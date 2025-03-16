package ac.kr.deu.connect.luck.foodtruck.controller

import ac.kr.deu.connect.luck.foodtruck.entity.FoodType
import ac.kr.deu.connect.luck.foodtruck.service.FoodTruckService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/food-truck")
class FoodTruckController(
    private val foodTruckService: FoodTruckService
) {
    @GetMapping
    fun foodTruck(
        model: Model,
        @RequestParam(required = false) foodTruckName: String?,
        @RequestParam(required = false) foodType: FoodType?,
        @ParameterObject pageRequest: PageRequest,
    ): String {
        model.addAttribute(
            "foodTruckList",
            foodTruckService.getFoodTrucks(
                name = foodTruckName,
                type = foodType,
                pageable = pageRequest
            )
        )
        return "food_truck/food_truck_list"
    }

    @GetMapping("/{foodTruckId}")
    fun foodTruckDetail(
        model: Model,
        @PathVariable foodTruckId: Long
    ): String {
        model.addAttribute(
            "foodTruck",
            foodTruckService.getFoodTruck(foodTruckId)
        )
        return "food_truck/food_truck_detail"
    }

    /** 푸드트럭 리뷰를 생성합니다. */
    @PostMapping(value = ["/{foodTruckId}/review"])
    fun createReview(
        model: Model,
        @PathVariable foodTruckId: Long
    ): String {
        return "redirect:/food-truck/${foodTruckId}"
    }

    /** 푸드트럭 리뷰를 삭제합니다. */
    @PostMapping(value = ["/{foodTruckId}/review/{reviewId}/delete"])
    fun deleteReview(
        model: Model,
        @PathVariable foodTruckId: Long,
        @PathVariable reviewId: Long
    ): String {
        return "redirect:/food-truck/${foodTruckId}"
    }
}
