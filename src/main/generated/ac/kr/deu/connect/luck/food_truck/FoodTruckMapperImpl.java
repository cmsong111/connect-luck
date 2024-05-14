package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckDetailResponse;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckHeader;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckMenuResponse;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckRequestV2;
import ac.kr.deu.connect.luck.food_truck.dto.FoodTruckReviewResponse;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckMenu;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import ac.kr.deu.connect.luck.food_truck.entity.FoodType;
import ac.kr.deu.connect.luck.user.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-14T13:29:17+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class FoodTruckMapperImpl implements FoodTruckMapper {

    @Override
    public FoodTruck toFoodTruck(FoodTruckRequestV2 foodTruckRequest) {
        if ( foodTruckRequest == null ) {
            return null;
        }

        FoodTruck.FoodTruckBuilder foodTruck = FoodTruck.builder();

        foodTruck.name( foodTruckRequest.getName() );
        foodTruck.description( foodTruckRequest.getDescription() );
        foodTruck.foodType( foodTruckRequest.getFoodType() );

        foodTruck.imageUrl( "imageUrl" );

        return foodTruck.build();
    }

    @Override
    public FoodTruckDetailResponse toFoodTruckDetailResponse(FoodTruck foodTruck) {
        if ( foodTruck == null ) {
            return null;
        }

        String managerName = null;
        Long id = null;
        String name = null;
        String description = null;
        String imageUrl = null;
        FoodType foodType = null;
        List<FoodTruckReviewResponse> reviews = null;
        List<FoodTruckMenuResponse> menus = null;

        managerName = foodTruckManagerName( foodTruck );
        id = foodTruck.getId();
        name = foodTruck.getName();
        description = foodTruck.getDescription();
        imageUrl = foodTruck.getImageUrl();
        foodType = foodTruck.getFoodType();
        reviews = foodTruckReviewListToFoodTruckReviewResponseList( foodTruck.getReviews() );
        menus = foodTruckMenuListToFoodTruckMenuResponseList( foodTruck.getMenus() );

        FoodTruckDetailResponse foodTruckDetailResponse = new FoodTruckDetailResponse( id, name, description, imageUrl, managerName, foodType, reviews, menus );

        return foodTruckDetailResponse;
    }

    @Override
    public FoodTruckHeader toFoodTruckHeader(FoodTruck foodTruck) {
        if ( foodTruck == null ) {
            return null;
        }

        String managerName = null;
        Long id = null;
        String name = null;
        String description = null;
        String imageUrl = null;
        FoodType foodType = null;

        managerName = foodTruckManagerName( foodTruck );
        id = foodTruck.getId();
        name = foodTruck.getName();
        description = foodTruck.getDescription();
        imageUrl = foodTruck.getImageUrl();
        foodType = foodTruck.getFoodType();

        double avgScore = foodTruck.getReviews().stream().mapToDouble(FoodTruckReview::getScore).average().orElse(0);
        int reviewCount = foodTruck.getReviews().size();

        FoodTruckHeader foodTruckHeader = new FoodTruckHeader( id, name, description, imageUrl, managerName, foodType, reviewCount, avgScore );

        return foodTruckHeader;
    }

    @Override
    public FoodTruckMenuResponse toFoodTruckMenuResponse(FoodTruckMenu foodTruckMenu) {
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

        FoodTruckMenuResponse foodTruckMenuResponse = new FoodTruckMenuResponse( id, name, description, imageUrl, price, createdAt, updatedAt );

        return foodTruckMenuResponse;
    }

    @Override
    public FoodTruckReviewResponse toFoodTruckReviewResponse(FoodTruckReview foodTruckReview) {
        if ( foodTruckReview == null ) {
            return null;
        }

        String authorName = null;
        Long id = null;
        String content = null;
        String imageUrl = null;
        int score = 0;
        String reply = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        authorName = foodTruckReviewAuthorName( foodTruckReview );
        id = foodTruckReview.getId();
        content = foodTruckReview.getContent();
        imageUrl = foodTruckReview.getImageUrl();
        score = foodTruckReview.getScore();
        reply = foodTruckReview.getReply();
        createdAt = foodTruckReview.getCreatedAt();
        updatedAt = foodTruckReview.getUpdatedAt();

        FoodTruckReviewResponse foodTruckReviewResponse = new FoodTruckReviewResponse( id, content, imageUrl, score, authorName, reply, createdAt, updatedAt );

        return foodTruckReviewResponse;
    }

    private String foodTruckManagerName(FoodTruck foodTruck) {
        if ( foodTruck == null ) {
            return null;
        }
        User manager = foodTruck.getManager();
        if ( manager == null ) {
            return null;
        }
        String name = manager.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected List<FoodTruckReviewResponse> foodTruckReviewListToFoodTruckReviewResponseList(List<FoodTruckReview> list) {
        if ( list == null ) {
            return null;
        }

        List<FoodTruckReviewResponse> list1 = new ArrayList<FoodTruckReviewResponse>( list.size() );
        for ( FoodTruckReview foodTruckReview : list ) {
            list1.add( toFoodTruckReviewResponse( foodTruckReview ) );
        }

        return list1;
    }

    protected List<FoodTruckMenuResponse> foodTruckMenuListToFoodTruckMenuResponseList(List<FoodTruckMenu> list) {
        if ( list == null ) {
            return null;
        }

        List<FoodTruckMenuResponse> list1 = new ArrayList<FoodTruckMenuResponse>( list.size() );
        for ( FoodTruckMenu foodTruckMenu : list ) {
            list1.add( toFoodTruckMenuResponse( foodTruckMenu ) );
        }

        return list1;
    }

    private String foodTruckReviewAuthorName(FoodTruckReview foodTruckReview) {
        if ( foodTruckReview == null ) {
            return null;
        }
        User author = foodTruckReview.getAuthor();
        if ( author == null ) {
            return null;
        }
        String name = author.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
