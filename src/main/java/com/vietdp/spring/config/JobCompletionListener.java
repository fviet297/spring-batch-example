package com.vietdp.spring.config;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.io.IOException;
@Component
public class JobCompletionListener extends JobExecutionListenerSupport {
    private SseEmitter emitter;
    private ExecutionContext executionContext;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        emitter = new SseEmitter();
    }
    @Override
    public void afterJob(JobExecution jobExecution) {
        this.getHello();
        try {
            SseEmitter.SseEventBuilder event = SseEmitter.event()
                    .data("Job completed successfully!")
                    .id("1")
                    .name("sse event - mvc");
            emitter.send(event);
            System.out.println("Job completed successfully!");

        } catch (IOException e) {
            emitter.completeWithError(e);
        }
    }
    public SseEmitter getEmitter(){
        return emitter;
    }
    public Mono<String> getHello() {
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:9192/hello")
                .retrieve()
                .bodyToMono(String.class);
    }
}

