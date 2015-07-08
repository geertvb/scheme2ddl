package com.googlecode.scheme2ddl.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.SimpleJobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.dao.*;
import org.springframework.batch.core.repository.support.SimpleJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@ImportResource("jobContext.xml")
public class BatchConfiguration {

    @Autowired
    public PlatformTransactionManager transactionManager;

    @Bean
    public JobInstanceDao jobInstanceDao() {
        return new MapJobInstanceDao();
    }

    @Bean
    public JobExecutionDao jobExecutionDao() {
        return new MapJobExecutionDao();
    }

    @Bean
    public StepExecutionDao stepExecutionDao() {
        return new MapStepExecutionDao();
    }

    @Bean
    public ExecutionContextDao executionContextDao() {
        return new MapExecutionContextDao();
    }

    @Bean
    public JobLauncher jobLauncher() {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        jobLauncher.setTaskExecutor(taskExecutor());
        return jobLauncher;
    }

    @Bean
    public JobRepository jobRepository() {
        return new SimpleJobRepository(
                jobInstanceDao(),
                jobExecutionDao(),
                stepExecutionDao(),
                executionContextDao());
    }

    @Bean
    public JobExplorer jobExplorer() {
        return new SimpleJobExplorer(
                jobInstanceDao(),
                jobExecutionDao(),
                stepExecutionDao(),
                executionContextDao()
        );
    }

//    @Bean
//    public SimpleStepFactoryBean simpleStep() {
//        SimpleStepFactoryBean simpleStep = new SimpleStepFactoryBean();
//        simpleStep.setTransactionManager(transactionManager);
//        simpleStep.setJobRepository(jobRepository());
//        simpleStep.setStartLimit(100);
//        simpleStep.setCommitInterval(1);
//        return simpleStep;
//    }

    @Bean
    public JobLauncherCommandLineRunner jobLauncherCommandLineRunner() {
        return new CustomJobLauncherCommandLineRunner(
                jobLauncher(),
                jobExplorer());
    }

    @Bean
    public SimpleAsyncTaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(4);
        return taskExecutor;
    }

}
