package ac.kr.deu.connect.luck.now;

import ac.kr.deu.connect.luck.common.BaseEntity;
import ac.kr.deu.connect.luck.food_truck.FoodTruck;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Now extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private FoodTruck foodTruck;
    private Double latitude; // 위도
    private Double longitude; // 경도
    private Boolean isOperating; // 운영중인지 여부

}
