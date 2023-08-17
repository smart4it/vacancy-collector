package ru.smart4it.taskmanager.core.model;

import org.springframework.scheduling.support.CronExpression;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Расписание событий.
 */
public record Schedule(List<ScheduleEvent> events) {

    /**
     * Возвращает список событий для которых наступило время выполнения.
     * <p>
     * Вычисляется время следующего выполнения события и если оно уже
     * наступило (currentTime <= nextExecution), то оно добавляется в
     * список наступивших событий.
     *
     * @param currentTime текущее время по которому проверяется время
     *                    наступления события.
     * @return список наступивших событий {@code ScheduleEvent}.
     */
    public List<ScheduleEvent> getTriggeredEvents(OffsetDateTime currentTime) {
        List<ScheduleEvent> triggeredEvents = new ArrayList<>();
        for (ScheduleEvent event : events) {
            OffsetDateTime lastExecutionTime = event.getExecutionTime();
            OffsetDateTime nextExecution = CronExpression.parse(event.getCronExpression()).next(lastExecutionTime);
            if (nextExecution == null) {
                continue;
            }
            if (currentTime.isAfter(nextExecution)) {
                event.setExecutionTime(currentTime);
                triggeredEvents.add(event);
            }
        }
        return triggeredEvents;
    }
}
