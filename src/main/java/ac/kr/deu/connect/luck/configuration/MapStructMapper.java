package ac.kr.deu.connect.luck.configuration;

import ac.kr.deu.connect.luck.auth.LoginRequest;
import ac.kr.deu.connect.luck.auth.SignUpRequest;
import ac.kr.deu.connect.luck.event.Event;
import ac.kr.deu.connect.luck.event.EventRequest;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckDetailResponse;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuRequest;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewRequest;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckMenu;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import ac.kr.deu.connect.luck.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    User toUser(SignUpRequest signUpRequest);

    User toUser(LoginRequest loginRequest);

    @Mapping(target = "status", defaultValue = "BEFORE_APPLICATION", ignore = true )
    @Mapping(source = "managerId", target = "manager.id")
    Event toEvent(EventRequest eventRequest);

    FoodTruck toFoodTruck(FoodTruckRequest foodTruckRequest);

    FoodTruckDetailResponse toFoodTruckDetailResponse(FoodTruck foodTruck, List<FoodTruckReview> reviews, List<FoodTruckMenu> menus);

    FoodTruckRequest toFoodTruckRequest(FoodTruck foodTruck);

    FoodTruckMenu toFoodTruckMenu(FoodTruckMenuRequest foodTruckMenuRequest);

    @Mapping(source = "foodTruckId", target = "foodTruck.id")
    @Mapping(source = "authorId", target = "author.id")
    FoodTruckReview toReview(FoodTruckReviewRequest foodTruckReviewRequest);

}
