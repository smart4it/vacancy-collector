package ru.smart4it.taskmanager.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.UUID;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {

    private UUID id;

    private String title;

    private TaskType type;

    private String specification;

    private ScheduleEvent event;
}
