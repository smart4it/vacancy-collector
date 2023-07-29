package ru.smart4it.collector.task;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "hh_vacancy_task_instance")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskHhVacancyEntity {

    @EmbeddedId
    private TaskHhVacancyEntityId id;


    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public static class TaskHhVacancyEntityId {

        @Column(name = "task_instance_id")
        private UUID taskId;

        @Column(name = "hh_vacancy_id")
        private String dataId;
    }
}