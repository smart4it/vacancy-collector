package ru.smart4it.parser.hh.task;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "task_instance")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskEntity {

    @Id
    private UUID id;

    private String title;

    @Type(JsonType.class)
    private String specification;

    private OffsetDateTime startTime;

    private OffsetDateTime completionTime;

    private boolean completed;
}