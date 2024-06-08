package com.lecuong.sourcebase.schedule.quartzv2.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

/**
 * @author CuongLM
 * @created 08/06/2024 - 11:29
 * @project source-base
 */
@Component
public class SampleJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Thực hiện Sample Job...");
        // Thêm logic của job vào đây
    }
}
