//package ac.kr.deu.connect.luck.foodtruck.working.controller
//
//import ac.kr.deu.connect.luck.foodtruck.working.NowService
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.stereotype.Controller
//import org.springframework.ui.Model
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestParam
//
//
//@Controller
//@RequestMapping("/now")
//class NowController(
//    val nowService: NowService
//) {
//
//    @Value("\${api-key.kakao}")
//    private lateinit var apiKey: String
//
//    @GetMapping
//    fun nearNow(
//        model: Model,
//        @RequestParam latitude: Double,
//        @RequestParam longitude: Double,
//        @RequestParam radiusKm: Double = 5.0,
//        @RequestParam(defaultValue = "30") size: Int,
//    ): String {
//        model.addAttribute(
//            "apiKey",
//            apiKey
//        )
//        model.addAttribute(
//            "nowTruckList",
//            nowService.findNearbyFoodTrucks(latitude, longitude, radiusKm)
//        )
//        return "now/now-map"
//    }
//
//}
