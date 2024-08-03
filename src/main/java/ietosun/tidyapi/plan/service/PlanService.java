package ietosun.tidyapi.plan.service;

import ietosun.tidyapi.plan.PlanRepository;
import ietosun.tidyapi.plan.entity.Plan;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlanRepository planRepository;

    public List<Plan> getList() {
        return this.planRepository.findAll();
    }

    public Optional<Plan> getPlanById(Long id) {
        return this.planRepository.findById(id);
    }
}
