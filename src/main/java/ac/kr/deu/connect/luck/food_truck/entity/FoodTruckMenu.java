package ac.kr.deu.connect.luck.food_truck.entity;

import ac.kr.deu.connect.luck.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Column(columnDefinition = "LONGTEXT")
    private String imageUrl;
    private int price;

    @JsonBackReference
    @ManyToOne
    private FoodTruck foodTruck;
}
