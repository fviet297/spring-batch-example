package com.vietdp.spring.batch.reader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.vietdp.spring.dto.DsEcomItem;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.annotation.BeforeJob;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class ProductReader implements ItemReader<DsEcomItem> {
    private final JobRepository jobRepository;

    private JobExecution jobExecution;
    private ExecutionContext executionContext;
    private int count;
    private int itemCount = 0;
    private int itemCountJob = 0;

    public ProductReader(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public DsEcomItem read() throws Exception {
        if(count < 1) {
            count++;
            XmlMapper xmlMapper = new XmlMapper();
            DsEcomItem dsEcomItem = xmlMapper.readValue(new File("src/main/resources/xml/10_product_uat.xml"), DsEcomItem.class);
            return dsEcomItem;
        }else{
//            jobExecution.setStatus(BatchStatus.COMPLETED);
            return null;
        }
    }
    @BeforeStep
    private void getExecutionContext(StepExecution stepExecution) {
        this.jobExecution = stepExecution.getJobExecution();
        this.executionContext = this.jobExecution.getExecutionContext();
        System.out.println("Before Step Reader");
    }
    @AfterRead
    private void setNumRecordReader() {
        System.out.println("AfterRead  Reader");
        itemCount++;
    }
    @BeforeJob
    private void setCountItem(){
        System.out.println("BeforeJob Step Reader");

        this.executionContext.put("itemCountJob",this.itemCountJob);
    }

}
