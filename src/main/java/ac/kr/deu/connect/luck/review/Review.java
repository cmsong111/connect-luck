package ac.kr.deu.connect.luck.review;

import ac.kr.deu.connect.luck.common.BaseEntity;
import ac.kr.deu.connect.luck.food_truck.FoodTruck;
import ac.kr.deu.connect.luck.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private String imageUrl;
    private String reply;
    private int score;
    @ManyToOne
    private FoodTruck foodTruck;
    @ManyToOne
    private User writer;
}
