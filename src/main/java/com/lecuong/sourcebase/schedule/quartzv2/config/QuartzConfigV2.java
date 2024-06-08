package com.lecuong.sourcebase.schedule.quartzv2.config;

import com.lecuong.sourcebase.schedule.quartzv2.job.AnotherJob;
import com.lecuong.sourcebase.schedule.quartzv2.job.SampleJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author CuongLM
 * @created 08/06/2024 - 11:29
 * @project source-base
 */
@Configuration
public class QuartzConfigV2 {

    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder.newJob(SampleJob.class)
                .withIdentity("sampleJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger sampleJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?"); // Chạy mỗi 2 giây

        return TriggerBuilder.newTrigger()
                .forJob(sampleJobDetail())
                .withIdentity("sampleTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail anotherJobDetail() {
        return JobBuilder.newJob(AnotherJob.class)
                .withIdentity("anotherJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger anotherJobTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?"); // Chạy mỗi 5 giây

        return TriggerBuilder.newTrigger()
                .forJob(anotherJobDetail())
                .withIdentity("anotherTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }
}
