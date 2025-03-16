//package ac.kr.deu.connect.luck.foodtruck.working.controller
//
//import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckWorking
//import ac.kr.deu.connect.luck.foodtruck.working.NowService
//import ac.kr.deu.connect.luck.foodtruck.working.controller.data.NowRequest
//import ac.kr.deu.connect.luck.foodtruck.working.controller.data.NowUpdateForm
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//@RequestMapping("api/v1/now/food-truck")
//class NowRestController(
//    private val nowService: NowService,
//) {
//    @GetMapping
//    fun getNowFoodTruckList(
//        @RequestBody nowRequest: NowRequest
//    ): List<FoodTruckWorking> {
//        return nowService.findNearbyFoodTrucks(
//            latitude = nowRequest.latitude,
//            longitude = nowRequest.longitude,
//            radiusKm = nowRequest.radiusKm,
//        )
//    }
//
//    @PostMapping("/{foodTruckId}")
//    fun updateNow(
//        @PathVariable foodTruckId: Long,
//        @RequestBody nowUpdateForm: NowUpdateForm
//    ): FoodTruckWorking {
//        return nowService.updateNow(
//            foodTruckId = foodTruckId,
//            latitude = nowUpdateForm.latitude,
//            longitude = nowUpdateForm.longitude,
//            status = nowUpdateForm.status,
//        )
//    }
//}
