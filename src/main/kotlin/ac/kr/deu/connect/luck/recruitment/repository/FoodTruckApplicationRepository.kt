package ac.kr.deu.connect.luck.recruitment.repository

import ac.kr.deu.connect.luck.recruitment.entity.FoodTruckApplication
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FoodTruckApplicationRepository : JpaRepository<FoodTruckApplication, Long> {

}
