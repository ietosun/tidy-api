package ietosun.tidyapi.plan.service;

import ietosun.tidyapi.plan.PlanRepository;
import ietosun.tidyapi.plan.entity.Plan;
import ietosun.tidyapi.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PlanService {

    private final PlanRepository planRepository;
    private final UserRepository userRepository;

    public List<Plan> getList() {
        return this.planRepository.findAll();
    }

    public Optional<Plan> getPlanById(Long id) {
        return this.planRepository.findById(id);
    }

    public Plan createPlan(Plan plan, Long userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    plan.assignUser(user);
                    return this.planRepository.save(plan);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Plan updatePlan(Plan updatePlan) {
        return this.planRepository.save(updatePlan);
    }

    public void deletePlan(Long id) {
        Optional<Plan> planOptional = planRepository.findById(id);
        planOptional.ifPresent(plan -> {
            planRepository.delete(plan); // 논리 삭제 처리
        });
    }
}
