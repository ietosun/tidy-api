package ietosun.tidyapi.plan.controller;

import ietosun.tidyapi.plan.entity.Plan;
import ietosun.tidyapi.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
