package com.vietdp.spring.batch.config;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component
public class MyJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        // Do something before the job starts
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getExitStatus().equals(ExitStatus.COMPLETED)) {
            jobExecution.stop();
        }
    }
}

