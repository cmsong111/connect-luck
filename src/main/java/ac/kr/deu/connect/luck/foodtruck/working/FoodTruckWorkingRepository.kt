//package ac.kr.deu.connect.luck.foodtruck.working
//
//import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckWorking
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Query
//import org.springframework.data.repository.query.Param
//import org.springframework.stereotype.Repository
//
//@Repository
//interface FoodTruckWorkingRepository : JpaRepository<FoodTruckWorking, Long> {
//    @Query(
//        """
//    SELECT now FROM FoodTruckWorking now
//    WHERE (6371 * acos(cos(radians(:latitude)) * cos(radians(now.latitude)) * cos(radians(now.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(now.latitude)))) < :distance
//"""
//    )
//    fun findByLocationNear(
//        @Param("latitude") latitude: Double,
//        @Param("longitude") longitude: Double,
//        @Param("distance") distance: Double,
//    ): List<FoodTruckWorking>
//
//
//    /**
//     * 푸드트럭 식별자로 현재 상태 조회
//     */
//    fun findByFoodTruckId(foodTruckId: Long): FoodTruckWorking?
//}
