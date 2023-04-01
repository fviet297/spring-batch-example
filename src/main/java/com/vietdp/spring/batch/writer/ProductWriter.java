package com.vietdp.spring.batch.writer;

import com.vietdp.spring.batch.config.MyJobListener;
import com.vietdp.spring.batch.dto.DsEcomItem;
import com.vietdp.spring.batch.dto.DtEcomBranch;
import com.vietdp.spring.batch.dto.DtEcomItem;
import com.vietdp.spring.batch.entity.DtEcomBranchEntity;
import com.vietdp.spring.batch.entity.DtEcomItemEntity;
import com.vietdp.spring.batch.entity.DtEcomItemUOMEntity;
import com.vietdp.spring.batch.repository.BranchRepository;
import com.vietdp.spring.batch.repository.DtEcomItemRepository;
import com.vietdp.spring.batch.repository.ItemUOMERepository;
import org.apache.camel.builder.endpoint.dsl.SqlEndpointBuilderFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterJob;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductWriter implements ItemWriter<DsEcomItem> {
    private final BranchRepository branchRepository;
    private final DtEcomItemRepository dtEcomItemRepository;
    private final ItemUOMERepository itemUOMERepository;
    @Autowired
    private final MyJobListener myJobListener;

    public ProductWriter(BranchRepository branchRepository, DtEcomItemRepository dtEcomItemRepository, ItemUOMERepository itemUOMERepository, MyJobListener myJobListener) {
        this.branchRepository = branchRepository;
        this.dtEcomItemRepository = dtEcomItemRepository;
        this.itemUOMERepository = itemUOMERepository;
        this.myJobListener = myJobListener;
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

        for (DtEcomItem i : dtEcomBranch.getDtEcomItem()) {
            List<DtEcomItemUOMEntity> dtEcomItemUOMEntities = i.getDtEcomItemUOMs().stream().map(k -> {
                DtEcomItemUOMEntity dtEcomItemUOMEntity = new DtEcomItemUOMEntity();
                BeanUtils.copyProperties(k, dtEcomItemUOMEntity);
                return dtEcomItemUOMEntity;
            }).collect(Collectors.toList());
            itemUOMERepository.saveAll(dtEcomItemUOMEntities);
        }
    }

    @AfterStep
    private void setExecutionContext(StepExecution stepExecution) {
        System.out.println(String.format("========END JOBID={} LOG_IMPORT_PRODUCT :::: TOTAL PRODUCTS={} IMPORTED ========", stepExecution.getJobExecutionId(), stepExecution.getWriteCount()));
    }
}
