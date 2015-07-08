package com.googlecode.scheme2ddl.batch;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner;

import java.util.Arrays;
import java.util.Properties;

public class CustomJobLauncherCommandLineRunner extends JobLauncherCommandLineRunner {

    private static Log logger = LogFactory.getLog(CustomJobLauncherCommandLineRunner.class);

    public CustomJobLauncherCommandLineRunner(JobLauncher jobLauncher, JobExplorer jobExplorer) {
        super(jobLauncher, jobExplorer);
    }

    @Autowired
    protected ArgumentsConverter argumentsConverter;

    @Override
    public void run(String... args) throws JobExecutionException {
        logger.info("Running default command line with: " + Arrays.asList(args));
        Properties properties = argumentsConverter.getProperties(args);
        launchJobFromProperties(properties);
    }

}
