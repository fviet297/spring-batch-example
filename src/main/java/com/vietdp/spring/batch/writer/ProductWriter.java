package com.vietdp.spring.batch.writer;

import com.vietdp.spring.dto.DsEcomItem;
import com.vietdp.spring.dto.DtEcomBranch;
import com.vietdp.spring.dto.DtEcomItem;
import com.vietdp.spring.entity.DtEcomBranchEntity;
import com.vietdp.spring.entity.DtEcomItemEntity;
import com.vietdp.spring.entity.DtEcomItemUOMEntity;
import com.vietdp.spring.repository.BranchRepository;
import com.vietdp.spring.repository.DtEcomItemRepository;
import com.vietdp.spring.repository.ItemUOMERepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductWriter implements ItemWriter<DsEcomItem> {
    private final BranchRepository branchRepository;
    private final DtEcomItemRepository dtEcomItemRepository;
    private final ItemUOMERepository itemUOMERepository;
    private ExecutionContext executionContext;


    public ProductWriter(BranchRepository branchRepository, DtEcomItemRepository dtEcomItemRepository, ItemUOMERepository itemUOMERepository) {
        this.branchRepository = branchRepository;
        this.dtEcomItemRepository = dtEcomItemRepository;
        this.itemUOMERepository = itemUOMERepository;
    }

    @Override
    public void write(List<? extends DsEcomItem> list) throws Exception {
        DtEcomBranch dtEcomBranch = list.get(0).getDtEcomBranch();
        String branchId = dtEcomBranch.getBranchId();
        DtEcomBranchEntity dtEcomBranchEntity = new DtEcomBranchEntity();
        dtEcomBranchEntity.setBranchId(dtEcomBranch.getBranchId());
        branchRepository.save(dtEcomBranchEntity);
        List<DtEcomItemEntity> dtEcomItemEntities = dtEcomBranch.getDtEcomItem().stream().map(i -> {
            DtEcomItemEntity dtEcomItemEntity = new DtEcomItemEntity();
            BeanUtils.copyProperties(i, dtEcomItemEntity);
            dtEcomItemEntity.setBranchId(branchId);
            return dtEcomItemEntity;
        }).collect(Collectors.toList());
        dtEcomItemRepository.saveAll(dtEcomItemEntities);
        this.executionContext.put("itemCount", dtEcomItemEntities.size());


        for (DtEcomItem i : dtEcomBranch.getDtEcomItem()) {
            List<DtEcomItemUOMEntity> dtEcomItemUOMEntities = i.getDtEcomItemUOMs().stream().map(k -> {
                DtEcomItemUOMEntity dtEcomItemUOMEntity = new DtEcomItemUOMEntity();
                BeanUtils.copyProperties(k, dtEcomItemUOMEntity);
                return dtEcomItemUOMEntity;
            }).collect(Collectors.toList());
            itemUOMERepository.saveAll(dtEcomItemUOMEntities);
        }
    }

    @BeforeStep
    private void getExecutionContext(StepExecution stepExecution) {
        JobExecution jobExecution = stepExecution.getJobExecution();
        this.executionContext = jobExecution.getExecutionContext();
        System.out.println("Before Step Writter");

    }

    @AfterStep
    private void setExecutionContext(StepExecution stepExecution) {
        System.out.println("AfterStep Step Writter");

        System.out.println(String.format("========END JOBID= %s LOG_IMPORT_PRODUCT :::: TOTAL PRODUCTS= %s %s IMPORTED ========", stepExecution.getJobExecutionId(),
                stepExecution.getJobExecution().getExecutionContext().getInt("itemCount"),stepExecution.getWriteCount()
        ));
        int itemcountjob = stepExecution.getJobExecution().getExecutionContext().getInt("itemCount");
        this.executionContext.put("itemCountJob", ObjectUtils.anyNotNull(this.executionContext.get("itemCountJob"))?this.executionContext.getInt("itemCountJob"):0 +  itemcountjob);
        Flux.interval(Duration.ofSeconds(1))
                .map(seq -> ServerSentEvent.builder("SSE - " + seq + " " +  LocalTime.now().toString()).build());

    }

}
