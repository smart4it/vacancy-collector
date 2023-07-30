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
import ru.smart4it.taskmanager.core.repository.ScheduleRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskManagerService {

    private final ScheduleRepository scheduleRepository;

    private final KafkaTemplate<String, String> smart4itKafkaTemplate;

    /**
     * Метод проверяет расписание, создает задачу и отправляет в очередь на исполнение.
     */
    @Transactional
    @Scheduled(fixedDelayString = "${task-manager.startup.interval}")
    public void createTasks() {
        OffsetDateTime currentTime = OffsetDateTime.now();
        List<TaskTemplate> templates = scheduleRepository.findAllByDeletedIsFalse();
        for (TaskTemplate taskTemplate : templates) {
            if (needToDo(taskTemplate, currentTime)) {
                String taskAsJson = createTask(taskTemplate);
                String channel = taskTemplate.getType();
                smart4itKafkaTemplate.send(channel, taskAsJson);
            }

        }
    }

    private String createTask(TaskTemplate taskTemplate) {
        try {
            Map<String, Object> specMap = new ObjectMapper().readValue(taskTemplate.getSpecification(),
                                                                       new TypeReference<>() {
                                                                       });
            specMap.put("id", String.valueOf(UUID.randomUUID()));
            specMap.put("title", taskTemplate.getTitle());
            specMap.put("timestamp", taskTemplate.getLastExecution().toString());
            return new ObjectMapper().writeValueAsString(specMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean needToDo(TaskTemplate taskTemplate, OffsetDateTime currentTime) {
        OffsetDateTime lastExecution = taskTemplate.getLastExecution();
        OffsetDateTime nextExecution = CronExpression.parse(taskTemplate.getCronExpression()).next(lastExecution);
        if (nextExecution == null) {
            return false;
        }
        return currentTime.isAfter(nextExecution);
    }
}
