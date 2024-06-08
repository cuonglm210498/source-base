package com.lecuong.sourcebase.schedule.quartzv2.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author CuongLM
 * @created 08/06/2024 - 11:30
 * @project source-base
 */
public class AnotherJob implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Thực hiện Another Job...");
        // Thêm logic của job vào đây
    }
}
