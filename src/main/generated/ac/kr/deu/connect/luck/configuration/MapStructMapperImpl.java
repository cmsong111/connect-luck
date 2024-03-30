package ac.kr.deu.connect.luck.configuration;

import ac.kr.deu.connect.luck.auth.LoginRequest;
import ac.kr.deu.connect.luck.auth.SignUpRequest;
import ac.kr.deu.connect.luck.event.Event;
import ac.kr.deu.connect.luck.event.EventRequest;
import ac.kr.deu.connect.luck.food_truck.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.FoodTruckReviewRequestDto;
import ac.kr.deu.connect.luck.food_truck.FoodType;
import ac.kr.deu.connect.luck.user.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-31T00:57:08+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class MapStructMapperImpl implements MapStructMapper {

    @Override
    public User toUser(SignUpRequest signUpRequest) {
        if ( signUpRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( signUpRequest.email() );
        user.password( signUpRequest.password() );
        user.name( signUpRequest.name() );

        return user.build();
    }

    @Override
    public User toUser(LoginRequest loginRequest) {
        if ( loginRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.email( loginRequest.email() );
        user.password( loginRequest.password() );

        return user.build();
    }

    @Override
    public Event toEvent(EventRequest eventRequest) {
        if ( eventRequest == null ) {
            return null;
        }

        Event.EventBuilder event = Event.builder();

        event.manager( eventRequestToUser( eventRequest ) );
        event.title( eventRequest.title() );
        event.content( eventRequest.content() );
        event.zipCode( eventRequest.zipCode() );
        event.streetAddress( eventRequest.streetAddress() );
        event.detailAddress( eventRequest.detailAddress() );
        event.startAt( eventRequest.startAt() );
        event.endAt( eventRequest.endAt() );
        event.imageUrl( eventRequest.imageUrl() );

        return event.build();
    }

    @Override
    public FoodTruck toFoodTruck(FoodTruckRequest foodTruckRequest) {
        if ( foodTruckRequest == null ) {
            return null;
        }

        FoodTruck.FoodTruckBuilder foodTruck = FoodTruck.builder();

        foodTruck.name( foodTruckRequest.name() );
        foodTruck.description( foodTruckRequest.description() );
        foodTruck.imageUrl( foodTruckRequest.imageUrl() );
        foodTruck.foodType( foodTruckRequest.foodType() );

        return foodTruck.build();
    }

    @Override
    public FoodTruckRequest toFoodTruckRequest(FoodTruck foodTruck) {
        if ( foodTruck == null ) {
            return null;
        }

        String name = null;
        String description = null;
        String imageUrl = null;
        FoodType foodType = null;

        name = foodTruck.getName();
        description = foodTruck.getDescription();
        imageUrl = foodTruck.getImageUrl();
        foodType = foodTruck.getFoodType();

        FoodTruckRequest foodTruckRequest = new FoodTruckRequest( name, description, imageUrl, foodType );

        return foodTruckRequest;
    }

    @Override
    public FoodTruckReview toReview(FoodTruckReviewRequestDto foodTruckReviewRequest) {
        if ( foodTruckReviewRequest == null ) {
            return null;
        }

        FoodTruckReview.FoodTruckReviewBuilder foodTruckReview = FoodTruckReview.builder();

        foodTruckReview.foodTruck( foodTruckReviewRequestDtoToFoodTruck( foodTruckReviewRequest ) );
        foodTruckReview.author( foodTruckReviewRequestDtoToUser( foodTruckReviewRequest ) );
        foodTruckReview.content( foodTruckReviewRequest.content() );
        foodTruckReview.score( foodTruckReviewRequest.score() );
        foodTruckReview.imageUrl( foodTruckReviewRequest.imageUrl() );

        return foodTruckReview.build();
    }

    protected User eventRequestToUser(EventRequest eventRequest) {
        if ( eventRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( eventRequest.managerId() );

        return user.build();
    }

    protected FoodTruck foodTruckReviewRequestDtoToFoodTruck(FoodTruckReviewRequestDto foodTruckReviewRequestDto) {
        if ( foodTruckReviewRequestDto == null ) {
            return null;
        }

        FoodTruck.FoodTruckBuilder foodTruck = FoodTruck.builder();

        foodTruck.id( foodTruckReviewRequestDto.foodTruckId() );

        return foodTruck.build();
    }

    protected User foodTruckReviewRequestDtoToUser(FoodTruckReviewRequestDto foodTruckReviewRequestDto) {
        if ( foodTruckReviewRequestDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( foodTruckReviewRequestDto.authorId() );

        return user.build();
    }
}
