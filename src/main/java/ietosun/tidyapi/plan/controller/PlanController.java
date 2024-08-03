package ietosun.tidyapi.plan.controller;

import ietosun.tidyapi.plan.entity.Plan;
import ietosun.tidyapi.plan.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Controller
public class PlanController {

    private final PlanService planService;

    @GetMapping("api/v1/plans")
    @ResponseBody
    public List<Plan> list() {
        return this.planService.getList();
    }
}
