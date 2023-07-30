package ru.smart4it.taskmanager.core.model;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.support.CronExpression;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class Schedule {

    private final List<ScheduleEvent> events;

    public List<ScheduleEvent> getTriggeredEvents(OffsetDateTime currentTime) {
        List<ScheduleEvent> triggeredEvents = new ArrayList<>();
        for (ScheduleEvent event : events) {
            OffsetDateTime executionTime = event.getExecutionTime();
            OffsetDateTime nextExecution = CronExpression.parse(event.getCronExpression()).next(executionTime);
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
