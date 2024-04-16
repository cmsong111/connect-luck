package ac.kr.deu.connect.luck.event_application;

import ac.kr.deu.connect.luck.common.BaseEntity;
import ac.kr.deu.connect.luck.event.Event;
import ac.kr.deu.connect.luck.user.User;
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

    @ManyToOne
    private User foodTruckManager;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus status;

    private String comment;

}
