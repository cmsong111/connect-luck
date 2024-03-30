package ac.kr.deu.connect.luck.configuration;

import ac.kr.deu.connect.luck.auth.LoginRequest;
import ac.kr.deu.connect.luck.auth.SignUpRequest;
import ac.kr.deu.connect.luck.event.Event;
import ac.kr.deu.connect.luck.event.EventRequest;
import ac.kr.deu.connect.luck.food_truck.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.FoodTruckReviewRequestDto;
import ac.kr.deu.connect.luck.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    User toUser(SignUpRequest signUpRequest);

    User toUser(LoginRequest loginRequest);

    @Mapping(source = "managerId", target = "manager.id")
    Event toEvent(EventRequest eventRequest);

    @Mapping(target = "manager.id", ignore = true)
    FoodTruck toFoodTruck(FoodTruckRequest foodTruckRequest);

    @Mapping(source = "foodTruckId", target = "foodTruck.id")
    @Mapping(source = "authorId", target = "author.id")
    FoodTruckReview toReview(FoodTruckReviewRequestDto foodTruckReviewRequest);
}
