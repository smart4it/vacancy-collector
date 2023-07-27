package ru.smart4it.collector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.smart4it.collector.event.Task;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectorService {

    private final KafkaTemplate<String, String> smart4itKafkaTemplate;

    @KafkaListener(topics = "test2")
    public void createTaskToSaveVacancies(String message) {
        log.info("createTaskToSaveVacancies");
        Task task;
        try {
            task = new ObjectMapper().readValue(message, Task.class);
            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl = task.requests().get(0).url();
            Long pageSize = task.pagination().pageSize();
            Long total = task.pagination().total();
            String param = task.requests().get(0).queryParams().get(0).param();
            String value = task.requests().get(0).queryParams().get(0).value();
            log.info("createTaskToSaveVacancies: start={}, end={}", 0, total / pageSize);
            for (int i = 0; i < total / pageSize; i++) {

                URI uri = UriComponentsBuilder.fromHttpUrl(resourceUrl)
                        .queryParam(param, value)
                        .queryParam("page", i)
                        .queryParam("per_page", pageSize)
                        .build()
                        .encode()
                        .toUri();
                ResponseEntity<String> response
                        = restTemplate.getForEntity(uri, String.class);
                smart4itKafkaTemplate.send("test3", response.getBody());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }
    }
}