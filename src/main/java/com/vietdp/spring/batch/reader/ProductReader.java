package com.vietdp.spring.batch.reader;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.vietdp.spring.batch.dto.DsEcomItem;
import com.vietdp.spring.batch.dto.DtEcomBranch;
import com.vietdp.spring.batch.dto.DtEcomItem;
import com.vietdp.spring.batch.dto.ProductListDto;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.annotation.AfterRead;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;

@Component
public class ProductReader implements ItemReader<DsEcomItem> {
    private final JobRepository jobRepository;

    private JobExecution jobExecution;
    private ExecutionContext executionContext;
    private int count;


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
            return null;
        }
    }
}
