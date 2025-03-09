package ac.kr.deu.connect.luck.food_truck.entity;

import ac.kr.deu.connect.luck.common.entity.BaseEntity;
import ac.kr.deu.connect.luck.user.entity.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodTruckReview extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private int rating;
    private String imageUrl;
    private String reply;


    @ManyToOne
    @JsonBackReference
    private FoodTruck foodTruck;

    @ManyToOne
    @JsonBackReference
    private User author;
}
