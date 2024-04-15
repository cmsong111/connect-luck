package ac.kr.deu.connect.luck.configuration;

import ac.kr.deu.connect.luck.auth.LoginRequest;
import ac.kr.deu.connect.luck.auth.SignUpRequest;
import ac.kr.deu.connect.luck.event.Event;
import ac.kr.deu.connect.luck.event.EventRequest;
import ac.kr.deu.connect.luck.food_truck.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.FoodTruckDetailResponse;
import ac.kr.deu.connect.luck.food_truck.FoodTruckMenu;
import ac.kr.deu.connect.luck.food_truck.FoodTruckMenuRequest;
import ac.kr.deu.connect.luck.food_truck.FoodTruckMenuResponse;
import ac.kr.deu.connect.luck.food_truck.FoodTruckRequest;
import ac.kr.deu.connect.luck.food_truck.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.FoodTruckReviewRequest;
import ac.kr.deu.connect.luck.food_truck.FoodTruckReviewResponse;
import ac.kr.deu.connect.luck.food_truck.FoodType;
import ac.kr.deu.connect.luck.user.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-16T02:14:38+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Oracle Corporation)"
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
    public FoodTruckDetailResponse toFoodTruckDetailResponse(FoodTruck foodTruck, List<FoodTruckReview> reviews, List<FoodTruckMenu> menus) {
        if ( foodTruck == null && reviews == null && menus == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        String imageUrl = null;
        FoodType foodType = null;
        User manager = null;
        if ( foodTruck != null ) {
            id = foodTruck.getId();
            name = foodTruck.getName();
            description = foodTruck.getDescription();
            imageUrl = foodTruck.getImageUrl();
            foodType = foodTruck.getFoodType();
            manager = foodTruck.getManager();
        }
        List<FoodTruckReviewResponse> reviews1 = null;
        reviews1 = foodTruckReviewListToFoodTruckReviewResponseList( reviews );
        List<FoodTruckMenuResponse> menus1 = null;
        menus1 = foodTruckMenuListToFoodTruckMenuResponseList( menus );

        FoodTruckDetailResponse foodTruckDetailResponse = new FoodTruckDetailResponse( id, name, description, imageUrl, foodType, manager, reviews1, menus1 );

        return foodTruckDetailResponse;
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
    public FoodTruckMenu toFoodTruckMenu(FoodTruckMenuRequest foodTruckMenuRequest) {
        if ( foodTruckMenuRequest == null ) {
            return null;
        }

        FoodTruckMenu.FoodTruckMenuBuilder foodTruckMenu = FoodTruckMenu.builder();

        foodTruckMenu.name( foodTruckMenuRequest.name() );
        foodTruckMenu.description( foodTruckMenuRequest.description() );
        foodTruckMenu.imageUrl( foodTruckMenuRequest.imageUrl() );
        foodTruckMenu.price( foodTruckMenuRequest.price() );

        return foodTruckMenu.build();
    }

    @Override
    public FoodTruckReview toReview(FoodTruckReviewRequest foodTruckReviewRequest) {
        if ( foodTruckReviewRequest == null ) {
            return null;
        }

        FoodTruckReview.FoodTruckReviewBuilder foodTruckReview = FoodTruckReview.builder();

        foodTruckReview.foodTruck( foodTruckReviewRequestToFoodTruck( foodTruckReviewRequest ) );
        foodTruckReview.author( foodTruckReviewRequestToUser( foodTruckReviewRequest ) );
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

    protected FoodTruckReviewResponse foodTruckReviewToFoodTruckReviewResponse(FoodTruckReview foodTruckReview) {
        if ( foodTruckReview == null ) {
            return null;
        }

        Long id = null;
        String content = null;
        String imageUrl = null;
        int score = 0;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = foodTruckReview.getId();
        content = foodTruckReview.getContent();
        imageUrl = foodTruckReview.getImageUrl();
        score = foodTruckReview.getScore();
        createdAt = foodTruckReview.getCreatedAt();
        updatedAt = foodTruckReview.getUpdatedAt();

        String foodTruckName = null;
        String authorName = null;

        FoodTruckReviewResponse foodTruckReviewResponse = new FoodTruckReviewResponse( id, content, imageUrl, score, foodTruckName, authorName, createdAt, updatedAt );

        return foodTruckReviewResponse;
    }

    protected List<FoodTruckReviewResponse> foodTruckReviewListToFoodTruckReviewResponseList(List<FoodTruckReview> list) {
        if ( list == null ) {
            return null;
        }

        List<FoodTruckReviewResponse> list1 = new ArrayList<FoodTruckReviewResponse>( list.size() );
        for ( FoodTruckReview foodTruckReview : list ) {
            list1.add( foodTruckReviewToFoodTruckReviewResponse( foodTruckReview ) );
        }

        return list1;
    }

    protected FoodTruckMenuResponse foodTruckMenuToFoodTruckMenuResponse(FoodTruckMenu foodTruckMenu) {
        if ( foodTruckMenu == null ) {
            return null;
        }

        Long id = null;
        String name = null;
        String description = null;
        String imageUrl = null;
        int price = 0;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        id = foodTruckMenu.getId();
        name = foodTruckMenu.getName();
        description = foodTruckMenu.getDescription();
        imageUrl = foodTruckMenu.getImageUrl();
        price = foodTruckMenu.getPrice();
        createdAt = foodTruckMenu.getCreatedAt();
        updatedAt = foodTruckMenu.getUpdatedAt();

        String foodTruckName = null;

        FoodTruckMenuResponse foodTruckMenuResponse = new FoodTruckMenuResponse( id, name, description, imageUrl, price, foodTruckName, createdAt, updatedAt );

        return foodTruckMenuResponse;
    }

    protected List<FoodTruckMenuResponse> foodTruckMenuListToFoodTruckMenuResponseList(List<FoodTruckMenu> list) {
        if ( list == null ) {
            return null;
        }

        List<FoodTruckMenuResponse> list1 = new ArrayList<FoodTruckMenuResponse>( list.size() );
        for ( FoodTruckMenu foodTruckMenu : list ) {
            list1.add( foodTruckMenuToFoodTruckMenuResponse( foodTruckMenu ) );
        }

        return list1;
    }

    protected FoodTruck foodTruckReviewRequestToFoodTruck(FoodTruckReviewRequest foodTruckReviewRequest) {
        if ( foodTruckReviewRequest == null ) {
            return null;
        }

        FoodTruck.FoodTruckBuilder foodTruck = FoodTruck.builder();

        foodTruck.id( foodTruckReviewRequest.foodTruckId() );

        return foodTruck.build();
    }

    protected User foodTruckReviewRequestToUser(FoodTruckReviewRequest foodTruckReviewRequest) {
        if ( foodTruckReviewRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( foodTruckReviewRequest.authorId() );

        return user.build();
    }
}
