package ru.smart4it.taskmanager.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.smart4it.taskmanager.core.model.Schedule;
import ru.smart4it.taskmanager.core.model.ScheduleEvent;
import ru.smart4it.taskmanager.core.model.Task;
import ru.smart4it.taskmanager.core.model.TaskType;
import ru.smart4it.taskmanager.core.repository.ScheduleRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Менеджер задач.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskManagerService {

    private final ScheduleRepository scheduleRepository;

    private final KafkaTemplate<String, String> smart4itKafkaTemplate;

    private final ObjectMapper objectMapper;

    /**
     * Создание задач по расписанию.
     * <p>
     * Метод проверяет расписание, при необходимости создает задачи и отправляет
     * их в очередь на исполнение. Топик очереди определяется из типа задачи.
     */
    @Transactional
    @Scheduled(fixedDelayString = "${task-manager.startup.interval}")
    public void execute() {
        OffsetDateTime currentTime = OffsetDateTime.now();
        Schedule schedule = new Schedule(scheduleRepository.findAllByDeletedIsFalse());
        for (ScheduleEvent event : schedule.getTriggeredEvents(currentTime)) {
            String taskAsJson = createTask(event);
            TaskType topic = event.getType();
            smart4itKafkaTemplate.send(topic.toString(), taskAsJson);
        }
    }

    private String createTask(ScheduleEvent scheduleEvent) {
        try {
            Task task = objectMapper.readValue(scheduleEvent.getTaskTemplate(), Task.class);
            task.setId(UUID.randomUUID());
            task.setTitle(scheduleEvent.getTitle());
            task.setType(scheduleEvent.getType());
            task.setEvent(scheduleEvent);
            task.setSpecification(scheduleEvent.getTaskTemplate());
            return objectMapper.writeValueAsString(task);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
