package ac.kr.deu.connect.luck.configuration;

import ac.kr.deu.connect.luck.auth.LoginRequest;
import ac.kr.deu.connect.luck.auth.SignUpRequest;
import ac.kr.deu.connect.luck.event.Event;
import ac.kr.deu.connect.luck.event.EventRequest;
import ac.kr.deu.connect.luck.food_truck.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.FoodTruckRequest;
import ac.kr.deu.connect.luck.review.Review;
import ac.kr.deu.connect.luck.review.ReviewRequestDto;
import ac.kr.deu.connect.luck.user.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-18T02:31:03+0900",
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

        foodTruck.manager( foodTruckRequestToUser( foodTruckRequest ) );
        foodTruck.name( foodTruckRequest.name() );
        foodTruck.description( foodTruckRequest.description() );
        foodTruck.imageUrl( foodTruckRequest.imageUrl() );
        foodTruck.foodType( foodTruckRequest.foodType() );

        return foodTruck.build();
    }

    @Override
    public Review toReview(ReviewRequestDto reviewRequestDto) {
        if ( reviewRequestDto == null ) {
            return null;
        }

        Review.ReviewBuilder review = Review.builder();

        review.foodTruck( reviewRequestDtoToFoodTruck( reviewRequestDto ) );
        review.writer( reviewRequestDtoToUser( reviewRequestDto ) );
        review.content( reviewRequestDto.content() );
        review.imageUrl( reviewRequestDto.imageUrl() );
        review.score( reviewRequestDto.score() );

        return review.build();
    }

    protected User eventRequestToUser(EventRequest eventRequest) {
        if ( eventRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( eventRequest.managerId() );

        return user.build();
    }

    protected User foodTruckRequestToUser(FoodTruckRequest foodTruckRequest) {
        if ( foodTruckRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( foodTruckRequest.userId() );

        return user.build();
    }

    protected FoodTruck reviewRequestDtoToFoodTruck(ReviewRequestDto reviewRequestDto) {
        if ( reviewRequestDto == null ) {
            return null;
        }

        FoodTruck.FoodTruckBuilder foodTruck = FoodTruck.builder();

        foodTruck.id( reviewRequestDto.foodTruckId() );

        return foodTruck.build();
    }

    protected User reviewRequestDtoToUser(ReviewRequestDto reviewRequestDto) {
        if ( reviewRequestDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( reviewRequestDto.writerId() );

        return user.build();
    }
}
