package com.vietdp.spring.controller;

import com.vietdp.spring.config.JobCompletionListener;
import io.swagger.models.Model;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
public class IndexController {
    @Autowired
    private JobCompletionListener jobCompletionListener;
    private ExecutionContext executionContext;
    @Autowired
    JobExplorer jobExplorer;
    @GetMapping("/")
    public String index(final Model model) {
        return "index";
    }

}
