package com.vietdp.spring.batch.controller;

import com.vietdp.spring.batch.Response;
import com.vietdp.spring.batch.dto.ProductListDto;
import com.vietdp.spring.batch.service.ProductService;
import io.micrometer.core.lang.Nullable;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/importProducts")
    public ResponseEntity<Response> importXMLToDBJob(@RequestParam @Nullable String id) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
         final Response response = productService.importProduct(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
