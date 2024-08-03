package ietosun.tidyapi.plan.service;

import ietosun.tidyapi.plan.PlanRepository;
import ietosun.tidyapi.plan.entity.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlanRepository planRepository;

    public List<Plan> getList() {
        return this.planRepository.findAll();
    }
}
