package ac.kr.deu.connect.luck.foodtruck;

import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckMenuForm;
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckCreateForm;
import ac.kr.deu.connect.luck.foodtruck.controller.request.FoodTruckReviewCreateForm;
import ac.kr.deu.connect.luck.foodtruck.controller.response.FoodTruckDetailResponse;
import ac.kr.deu.connect.luck.foodtruck.controller.response.FoodTruckHeader;
import ac.kr.deu.connect.luck.foodtruck.controller.response.FoodTruckMenuResponse;
import ac.kr.deu.connect.luck.foodtruck.controller.response.FoodTruckReviewResponse;
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruck;
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckMenu;
import ac.kr.deu.connect.luck.foodtruck.entity.FoodTruckReview;
import ac.kr.deu.connect.luck.foodtruck.entity.FoodType;
import ac.kr.deu.connect.luck.user.entity.User;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-15T23:21:04+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Oracle Corporation)"
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

        foodTruck.imageUrl( "https://picsum.photos/1600/900" );

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

        Double avgRating = foodTruck.getReviews().stream().mapToDouble(FoodTruckReview::getRating).average().orElse(0);

        FoodTruckDetailResponse foodTruckDetailResponse = new FoodTruckDetailResponse( id, name, description, imageUrl, managerName, foodType, reviews, menus, avgRating );

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

        double avgRating = foodTruck.getReviews().stream().mapToDouble(FoodTruckReview::getRating).average().orElse(0);
        int reviewCount = foodTruck.getReviews().size();

        FoodTruckHeader foodTruckHeader = new FoodTruckHeader( id, name, description, imageUrl, managerName, foodType, reviewCount, avgRating );

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
        int rating = 0;
        String reply = null;
        LocalDateTime createdAt = null;
        LocalDateTime updatedAt = null;

        authorName = foodTruckReviewAuthorName( foodTruckReview );
        id = foodTruckReview.getId();
        content = foodTruckReview.getContent();
        imageUrl = foodTruckReview.getImageUrl();
        rating = foodTruckReview.getRating();
        reply = foodTruckReview.getReply();
        createdAt = foodTruckReview.getCreatedAt();
        updatedAt = foodTruckReview.getUpdatedAt();

        FoodTruckReviewResponse foodTruckReviewResponse = new FoodTruckReviewResponse( id, content, imageUrl, rating, authorName, reply, createdAt, updatedAt );

        return foodTruckReviewResponse;
    }

    @Override
    public FoodTruckRequestV2 toFoodTruckRequest(FoodTruck foodTruck) {
        if ( foodTruck == null ) {
            return null;
        }

        String name = null;
        String description = null;
        FoodType foodType = null;

        name = foodTruck.getName();
        description = foodTruck.getDescription();
        foodType = foodTruck.getFoodType();

        MultipartFile image = null;

        FoodTruckRequestV2 foodTruckRequestV2 = new FoodTruckRequestV2( name, description, image, foodType );

        return foodTruckRequestV2;
    }

    @Override
    public FoodTruckMenu toFoodTruckMenu(FoodTruckMenuRequest foodTruckMenuRequest) {
        if ( foodTruckMenuRequest == null ) {
            return null;
        }

        FoodTruckMenu.FoodTruckMenuBuilder foodTruckMenu = FoodTruckMenu.builder();

        foodTruckMenu.name( foodTruckMenuRequest.getName() );
        foodTruckMenu.description( foodTruckMenuRequest.getDescription() );
        foodTruckMenu.price( foodTruckMenuRequest.getPrice() );

        foodTruckMenu.imageUrl( "https://picsum.photos/1600/900" );

        return foodTruckMenu.build();
    }

    @Override
    public FoodTruckReview toFoodTruckReview(FoodTruckReviewRequest foodTruckReviewRequest) {
        if ( foodTruckReviewRequest == null ) {
            return null;
        }

        FoodTruckReview.FoodTruckReviewBuilder foodTruckReview = FoodTruckReview.builder();

        foodTruckReview.content( foodTruckReviewRequest.getContent() );
        foodTruckReview.rating( foodTruckReviewRequest.getRating() );

        return foodTruckReview.build();
    }

    @Override
    public FoodTruckReviewRequest toFoodTruckReviewRequest(FoodTruckReview foodTruckReview) {
        if ( foodTruckReview == null ) {
            return null;
        }

        String content = null;
        int rating = 0;

        content = foodTruckReview.getContent();
        rating = foodTruckReview.getRating();

        MultipartFile image = null;

        FoodTruckReviewRequest foodTruckReviewRequest = new FoodTruckReviewRequest( content, rating, image );

        return foodTruckReviewRequest;
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
