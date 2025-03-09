package ac.kr.deu.connect.luck.event.entity;

import ac.kr.deu.connect.luck.common.entity.BaseEntity;
import ac.kr.deu.connect.luck.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "LONGTEXT")
    private String content;
    private String zipCode;
    private String streetAddress;
    private String detailAddress;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String imageUrl;
    @ManyToOne
    private User manager;
    @Enumerated(EnumType.STRING)
    private EventStatus status;
}
