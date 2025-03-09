package ac.kr.deu.connect.luck.event.application;

import ac.kr.deu.connect.luck.common.entity.BaseEntity;
import ac.kr.deu.connect.luck.event.entity.Event;
import ac.kr.deu.connect.luck.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventApplication extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Event event;

    private Long foodTruckId;

    @ManyToOne
    private User foodTruckManager;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private String comment;

}
