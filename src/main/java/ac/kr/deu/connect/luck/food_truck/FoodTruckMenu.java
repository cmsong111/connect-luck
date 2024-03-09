package ac.kr.deu.connect.luck.food_truck;

import ac.kr.deu.connect.luck.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodTruckMenu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private int price;
    private int quantity;
    @ManyToOne
    private FoodTruck foodTruck;
}
