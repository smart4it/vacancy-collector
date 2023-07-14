package ru.smart4it.taskmanager.core;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.smart4it.taskmanager.core.model.TaskTemplate;
import ru.smart4it.taskmanager.core.repository.TaskTemplateRepository;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TaskManagerService {

    private final TaskTemplateRepository taskTemplateRepository;

    @Scheduled(cron = "0/5 * * * * *")
    @Transactional
    public void createTasks() {
        OffsetDateTime currentTime = OffsetDateTime.now(ZoneOffset.UTC);
        List<TaskTemplate> taskTemplates = taskTemplateRepository.findAllByDeletedIsFalse();
        for (TaskTemplate taskTemplate : taskTemplates) {
            CronExpression cronExpression = CronExpression.parse(taskTemplate.getCronExpression());
            OffsetDateTime lastExecution = taskTemplate.getLastExecution();
            OffsetDateTime nextExecution = cronExpression.next(currentTime);
            if (currentTime.isAfter(lastExecution)) {
                taskTemplate.setLastExecution(nextExecution);
            }
        }
    }

}
