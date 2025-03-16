package ac.kr.deu.connect.luck.foodtruck.controller

import ac.kr.deu.connect.luck.common.controller.data.AuthenticatedUser
import ac.kr.deu.connect.luck.foodtruck.service.FoodTruckAdminService
import ac.kr.deu.connect.luck.foodtruck.service.FoodTruckService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/admin/food-truck")
@PreAuthorize("hasRole('ROLE_FOOD_TRUCK_MANAGER')")
class FoodTruckAdminController(
    private val foodTruckService: FoodTruckService,
    private val foodTruckAdminService: FoodTruckAdminService,
) {
    /** 푸드트럭 관리자 페이지를 반환합니다. */
    @GetMapping
    fun foodTruckAdmin(
        model: Model,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): String {
        return "food_truck/food_truck_admin"
    }

    /** 푸드트럭 생성 폼을 반환합니다. */
    @GetMapping("/create")
    fun createFoodTruckForm(
        model: Model,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): String {
        return "food_truck/food_truck_create"
    }

    /** 푸드트럭을 생성합니다. */
    @PostMapping("/create")
    fun createFoodTruck(
        model: Model,
        @AuthenticationPrincipal authenticatedUser: AuthenticatedUser,
    ): String {
        return "redirect:/admin/food-truck/${1}"
    }

    /** 푸드트럭 상세 페이지를 반환합니다. */
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

    /** 푸드트럭 수정 폼을 반환합니다. */
    @GetMapping("/{foodTruckId}/edit")
    fun editFoodTruckForm(
        model: Model,
        @PathVariable foodTruckId: Long
    ): String {
        model.addAttribute(
            "foodTruck",
            foodTruckService.getFoodTruck(foodTruckId)
        )
        return "food_truck/food_truck_edit"
    }

    /** 푸드트럭을 수정합니다. */
    @PostMapping("/{foodTruckId}/edit")
    fun editFoodTruck(
        model: Model,
        @PathVariable foodTruckId: Long
    ): String {
        return "redirect:/admin/food-truck/${foodTruckId}"
    }

    /** 푸드트럭을 삭제합니다. */
    @PostMapping("/{foodTruckId}/delete")
    fun deleteFoodTruck(
        model: Model,
        @PathVariable foodTruckId: Long
    ): String {
        return "redirect:/admin/food-truck"
    }

    /** 푸드트럭 메뉴 생성 폼을 반환합니다. */
    @GetMapping("/{foodTruckId}/menu/")
    fun createFoodTruckMenuForm(
        model: Model,
        @PathVariable foodTruckId: Long
    ): String {
        model.addAttribute(
            "foodTruck",
            foodTruckService.getFoodTruck(foodTruckId)
        )
        return "food_truck/food_truck_menu_create"
    }

    /** 푸드트럭 메뉴를 생성합니다. */
    @PostMapping("/{foodTruckId}/menu")
    fun createFoodTruckMenu(
        model: Model,
        @PathVariable foodTruckId: Long
    ): String {
        return "redirect:/admin/food-truck/${foodTruckId}"
    }
}
