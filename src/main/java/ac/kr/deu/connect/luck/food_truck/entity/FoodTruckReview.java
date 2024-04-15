package ac.kr.deu.connect.luck.food_truck.entity;

import ac.kr.deu.connect.luck.common.BaseEntity;
import ac.kr.deu.connect.luck.user.User;
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
    private int score;
    private String imageUrl;
    private String reply;
    @ManyToOne
    private FoodTruck foodTruck;
    @ManyToOne
    private User author;
}