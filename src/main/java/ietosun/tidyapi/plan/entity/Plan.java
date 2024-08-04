package ietosun.tidyapi.plan.entity;

import ietosun.tidyapi.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Plan {

    @Id
    @Column(name = "plan_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "modify_date", nullable = false)
    private LocalDateTime modifyDate;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "alert_date", nullable = false)
    private LocalDateTime alertDate;

    @Column(name = "disclosure", nullable = false)
    private boolean disclosure;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // user_id로 컬럼 이름 지정 (안 할시, user_user_id 라는 이름으로 생성됨)
    private User user;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createDate = now;
        this.modifyDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifyDate = LocalDateTime.now();
    }

    public void assignUser(User user) {
        this.user = user;
    }
}
