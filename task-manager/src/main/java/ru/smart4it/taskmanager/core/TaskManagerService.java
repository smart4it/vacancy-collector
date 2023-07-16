package ru.smart4it.taskmanager.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.smart4it.taskmanager.core.model.TaskTemplate;
import ru.smart4it.taskmanager.core.repository.TaskTemplateRepository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskManagerService {

    private final TaskTemplateRepository taskTemplateRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Scheduled(cron = "0/5 * * * * *")
    @Transactional
    public void createTasks() {
        OffsetDateTime currentTime = OffsetDateTime.now();
        List<TaskTemplate> taskTemplates = taskTemplateRepository.findAllByDeletedIsFalse();
        for (TaskTemplate taskTemplate : taskTemplates) {
            OffsetDateTime lastExecution = taskTemplate.getLastExecution();
            OffsetDateTime nextExecution = CronExpression.parse(taskTemplate.getCronExpression()).next(lastExecution);
            if (nextExecution == null) {
                return;
            }
            try {

                Map<String, Object> specMap = new ObjectMapper().readValue(taskTemplate.getSpecification(),
                                                                           new TypeReference<>() {});
                specMap.put("id", String.valueOf(UUID.randomUUID()));
                specMap.put("title", taskTemplate.getTitle());
                specMap.put("timestamp", lastExecution.toString());
                String specification = new ObjectMapper().writeValueAsString(specMap);
                if (currentTime.isAfter(nextExecution)) {
                    taskTemplate.setLastExecution(currentTime);
                    kafkaTemplate.send("test", specification);
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
