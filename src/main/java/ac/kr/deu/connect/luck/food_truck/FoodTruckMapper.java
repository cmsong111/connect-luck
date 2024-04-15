package ac.kr.deu.connect.luck.food_truck;


import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckDetailResponse;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuRequest;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewRequest;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckMenu;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodTruckMapper {

    FoodTruck toFoodTruck(FoodTruckRequest foodTruckRequest);

    FoodTruckDetailResponse toFoodTruckDetailResponse(FoodTruck foodTruck, List<FoodTruckReview> reviews, List<FoodTruckMenu> menus);

    FoodTruckRequest toFoodTruckRequest(FoodTruck foodTruck);

    FoodTruckMenu toFoodTruckMenu(FoodTruckMenuRequest foodTruckMenuRequest);

    @Mapping(source = "foodTruckId", target = "foodTruck.id")
    @Mapping(source = "authorId", target = "author.id")
    FoodTruckReview toReview(FoodTruckReviewRequest foodTruckReviewRequest);

}
