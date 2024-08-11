package ietosun.tidyapi.plan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class PlanUpdateDTO {
    private final String title;
    private final String content;
    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final LocalDateTime alertDate;
}
