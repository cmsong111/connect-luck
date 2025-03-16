//package ac.kr.deu.connect.luck.foodtruck.working
//
//import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckWorking
//import org.springframework.stereotype.Service
//import org.springframework.transaction.annotation.Transactional
//
//@Service
//class NowService(
//    private val nowRepository: FoodTruckWorkingRepository
//) {
//
//    @Transactional
//    fun updateNow(
//        foodTruckId: Long,
//        latitude: Double,
//        longitude: Double,
//        status: FoodTruckWorking.Status = FoodTruckWorking.Status.WORKING,
//    ): FoodTruckWorking {
//        TODO("Not implemented")
//        val now = nowRepository.findByFoodTruckId(foodTruckId)
//            ?: FoodTruckWorking(foodTruckId = foodTruckId)
//
//        now.status = status
//        now.latitude = latitude
//        now.longitude = longitude
//
//        return nowRepository.save(now)
//    }
//
//    @Transactional(readOnly = true)
//    fun findNearbyFoodTrucks(
//        latitude: Double,
//        longitude: Double,
//        radiusKm: Double = 5.0,
//    ): List<FoodTruckWorking> {
//        return nowRepository.findByLocationNear(
//            latitude = latitude,
//            longitude = longitude,
//            distance = radiusKm,
//        )
//    }
//}
