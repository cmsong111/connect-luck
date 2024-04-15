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
public class FoodTruck extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    @ManyToOne
    private User manager;
    @Enumerated(EnumType.STRING)
    private FoodType foodType;
}
