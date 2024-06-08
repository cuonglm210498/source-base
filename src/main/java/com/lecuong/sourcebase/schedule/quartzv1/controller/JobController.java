package com.lecuong.sourcebase.schedule.quartzv1.controller;

import com.lecuong.sourcebase.modal.response.BaseResponseV2;
import com.lecuong.sourcebase.schedule.quartzv1.model.TaskDefinition;
import com.lecuong.sourcebase.schedule.quartzv1.service.TaskDefinitionBean;
import com.lecuong.sourcebase.schedule.quartzv1.service.TaskSchedulingService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @author CuongLM
 * @created 08/06/2024 - 10:51
 * @project source-base
 */
@Data
@RestController
@RequestMapping("/api/v1/schedule")
public class JobController {

    private final TaskDefinitionBean taskDefinitionBean;
    private final TaskSchedulingService taskSchedulingService;

    @PostMapping(path="/taskdef", consumes = "application/json", produces="application/json")
    public ResponseEntity<BaseResponseV2<String>> scheduleATask(@RequestBody TaskDefinition taskDefinition) {
        String jobId = UUID.randomUUID().toString();
        taskDefinitionBean.setTaskDefinition(taskDefinition);
        taskSchedulingService.scheduleATask(jobId, taskDefinitionBean, taskDefinition.getCronExpression());
        return ResponseEntity.ok(BaseResponseV2.ofSuccess(jobId));
    }

    @DeleteMapping(path="/remove/{jobId}")
    public ResponseEntity<BaseResponseV2<Void>> removeJob(@PathVariable String jobId) {
        taskSchedulingService.removeScheduledTask(jobId);
        return ResponseEntity.ok(BaseResponseV2.ofSuccess(null));
    }
}
