package ru.smart4it.collector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.smart4it.collector.event.Task;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectorService {

    private final KafkaTemplate<String, String> smart4itKafkaTemplate;

    @KafkaListener(topics = "test2")
    public void createTaskToSaveVacancies(String message) {
        Task task;
        try {
            task = new ObjectMapper().readValue(message, Task.class);
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = task.requests().get(0).url();
            ResponseEntity<String> response
                    = restTemplate.getForEntity(fooResourceUrl, String.class);
            smart4itKafkaTemplate.send("test3", response.getBody());
        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }
    }
}