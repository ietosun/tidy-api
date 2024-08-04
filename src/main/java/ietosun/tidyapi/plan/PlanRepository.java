package ietosun.tidyapi.plan;

import ietosun.tidyapi.plan.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
