package ac.kr.deu.connect.luck.user;

import ac.kr.deu.connect.luck.common.BaseEntity;
import ac.kr.deu.connect.luck.event.Event;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruck;
import ac.kr.deu.connect.luck.food_truck.entity.FoodTruckReview;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "users")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    @Column(unique = true)
    private String phone;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    List<UserRole> roles;

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<FoodTruckReview> reviews;

    @JsonIgnore
    @OneToMany(mappedBy = "manager", cascade = CascadeType.REMOVE)
    @JsonManagedReference
    private List<FoodTruck> foodTrucks;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Event> events;
}
