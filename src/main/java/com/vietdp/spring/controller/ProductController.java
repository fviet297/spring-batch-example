package com.vietdp.spring.controller;

import com.vietdp.spring.Response;
import com.vietdp.spring.config.JobCompletionListener;
import com.vietdp.spring.service.ProductService;
import io.micrometer.core.lang.Nullable;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private JobCompletionListener jobCompletionListener;
    @Autowired
    private SseEmitter sseEmitter;
    @PostMapping("/importProducts")
    public ResponseEntity<Response> importXMLToDBJob(@RequestParam @Nullable String id) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        final Response response = productService.importProduct(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/jobstatus")
    public SseEmitter handleRequest() {
        return sseEmitter;
    }

}