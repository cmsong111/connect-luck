package ac.kr.deu.connect.luck.food_truck.entity;

import ac.kr.deu.connect.luck.common.BaseEntity;
import ac.kr.deu.connect.luck.now.Now;
import ac.kr.deu.connect.luck.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<FoodTruckMenu> menus;

    @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<FoodTruckReview> reviews;

    @OneToMany(mappedBy = "foodTruck", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<Now> nows;
}
