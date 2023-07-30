package ru.smart4it.taskmanager.core.model;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "schedule")
public class TaskTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "type")
    private String type;

    @Type(JsonType.class)
    @Column(name = "specification")
    private String specification;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "last_execution")
    private OffsetDateTime lastExecution;

    @Column(name = "deleted")
    private Boolean deleted;
}
