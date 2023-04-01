package com.vietdp.spring.batch.service;

import com.vietdp.spring.batch.Response;
import com.vietdp.spring.batch.config.MyJobListener;
import com.vietdp.spring.batch.dto.*;
import com.vietdp.spring.batch.reader.ProductReader;
import com.vietdp.spring.batch.repository.BranchRepository;
import com.vietdp.spring.batch.repository.DtEcomItemRepository;
import com.vietdp.spring.batch.repository.ItemUOMERepository;
import com.vietdp.spring.batch.writer.ProductWriter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class ProductService implements ProductServiceInterface {
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private BranchRepository branchRepository;
    @Autowired
    private DtEcomItemRepository dtEcomItemRepository;
    @Autowired
    private ItemUOMERepository itemUOMERepository;
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private MyJobListener myJobListener;
    @Autowired
    private JobExplorer jobExplorer;

    @Override
    public Response importProduct(String idJob) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        boolean isCompleted = false;
        String percent;
        Map<String, Object> data = new HashMap<>();
        Response response = new Response();
        if (StringUtils.isEmpty(idJob)) {
            final Calendar calendar = Calendar.getInstance();
            long timenow = calendar.getTimeInMillis();
            Step step = stepBuilderFactory.get("importProduct").<DsEcomItem, DsEcomItem>chunk(10)
                    .reader(new ProductReader(jobRepository))
                    .chunk(1)
                    .writer(new ProductWriter(branchRepository, dtEcomItemRepository, itemUOMERepository, myJobListener))
                    .build();
            final Job productJob = jobBuilderFactory.get("ProductJob").incrementer(new RunIdIncrementer()).flow(step)
                    .end().build();
            final JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", timenow).toJobParameters();
            final JobExecution jobExecution = jobLauncher.run(productJob, jobParameters);
            data.put("jobId",jobExecution.getJobId());
            response.setData(data);
            return response;
        } else {
            final long longJobId = Long.parseLong(idJob);
            final JobExecution jobExecution = jobExplorer.getJobExecution(longJobId);
            final ExecutionContext executionContext = jobExecution.getExecutionContext();

            if (!jobExecution.isRunning()) {
                isCompleted = true;
            }
            percent = String.valueOf(executionContext.getInt("percent_imported", 0));
        }
        data.put("isCompleted", isCompleted);
        data.put("percent", percent);
        response.setData(data);
        return response;
    }
}
