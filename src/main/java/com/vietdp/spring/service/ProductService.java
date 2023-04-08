package com.vietdp.spring.service;

import com.vietdp.spring.Response;
import com.vietdp.spring.config.JobCompletionListener;
import com.vietdp.spring.config.JobCompletionNotificationListener;
import com.vietdp.spring.dto.DsEcomItem;
import com.vietdp.spring.batch.reader.ProductReader;
import com.vietdp.spring.repository.BranchRepository;
import com.vietdp.spring.repository.DtEcomItemRepository;
import com.vietdp.spring.repository.ItemUOMERepository;
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
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.IOException;
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
    private WebClient.Builder webClientBuilder;

    @Autowired
    private JobExplorer jobExplorer;
    @Autowired
    private SseEmitter sseEmitter;
    @Override
    public Response importProduct(String idJob) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        boolean isCompleted = false;
        String percent;
        Map<String, Object> data = new HashMap<>();
        Response response = new Response();
        if (StringUtils.isEmpty(idJob)) {
            final Calendar calendar = Calendar.getInstance();
            long timenow = calendar.getTimeInMillis();
            Step step = stepBuilderFactory.get("importProduct").<DsEcomItem, DsEcomItem>chunk(1)
                    .reader(new ProductReader(jobRepository))
                    .chunk(1)
                    .writer(new ProductWriter(branchRepository, dtEcomItemRepository, itemUOMERepository))
                    .build();
            final Job productJob = jobBuilderFactory.get("ProductJob").listener(new JobExecutionListener() {
                        @Override
                        public void beforeJob(JobExecution jobExecution) {

                        }

                        @Override
                        public void afterJob(JobExecution jobExecution) {
                            try {
                                sseEmitter.send("Job finished successfully");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).incrementer(new RunIdIncrementer()).flow(step)
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
