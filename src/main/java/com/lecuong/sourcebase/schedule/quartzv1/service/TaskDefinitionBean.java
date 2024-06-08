package com.lecuong.sourcebase.schedule.quartzv1.service;

import com.lecuong.sourcebase.schedule.quartzv1.model.TaskDefinition;
import org.springframework.stereotype.Service;

/**
 * @author CuongLM
 * @created 08/06/2024 - 10:46
 * @project source-base
 */
@Service
public class TaskDefinitionBean implements Runnable {

    private TaskDefinition taskDefinition;

    @Override
    public void run() {
        System.out.println("Running action: " + taskDefinition.getActionType());
        System.out.println("With Data: " + taskDefinition.getData());

        // Thêm logic của job vào đây
    }


    public TaskDefinition getTaskDefinition() {
        return taskDefinition;
    }

    public void setTaskDefinition(TaskDefinition taskDefinition) {
        this.taskDefinition = taskDefinition;
    }
}
