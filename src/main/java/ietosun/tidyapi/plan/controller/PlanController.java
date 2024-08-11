package ietosun.tidyapi.plan.controller;

import ietosun.tidyapi.plan.dto.PlanUpdateDTO;
import ietosun.tidyapi.plan.entity.Plan;
import ietosun.tidyapi.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/plans")
@Controller
public class PlanController {

    private final PlanService planService;

    @GetMapping
    @ResponseBody
    public List<Plan> list() {
        return this.planService.getList();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Plan getPlan(@PathVariable Long id) {
        return this.planService.getPlanById(id)
                .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    @PostMapping
    @ResponseBody
    public Plan createPlan(@RequestBody Plan plan, @RequestParam Long userId) {
        return this.planService.createPlan(plan, userId);
    }

    @PutMapping("/{id}")
    public Plan updatePlan(@PathVariable Long id, @RequestBody PlanUpdateDTO updateRequest) {
        return planService.getPlanById(id)
                .map(plan -> {
                    Plan updatePlan = Plan.builder()
                            .id(plan.getId())
                            .title(updateRequest.getTitle())
                            .content(updateRequest.getContent())
                            .startDate(updateRequest.getStartDate())
                            .endDate(updateRequest.getEndDate())
                            .alertDate(updateRequest.getAlertDate())
                            .disclosure(plan.isDisclosure())
                            .user(plan.getUser())
                            .createDate(plan.getCreateDate())
                            .modifyDate(LocalDateTime.now())
                            .build();

                    return planService.updatePlan(updatePlan);
                })
                .orElseThrow(() -> new RuntimeException("Plan not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.noContent().build(); // 204 No Content 응답
    }
}
