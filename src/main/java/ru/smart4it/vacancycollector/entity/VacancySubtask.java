package ru.smart4it.vacancycollector.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "vacancy_subtask")
@AllArgsConstructor
@NoArgsConstructor
public class VacancySubtask {

    @Id
    private UUID id;
    private int page;
    private boolean done;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_task_id")
    private VacancyTask vacancyTask;
}