package ru.smart4it.vacancycollector.vacancies.snapshot;

import jakarta.persistence.*;
import lombok.*;
import ru.smart4it.vacancycollector.vacancies.task.Task;
import ru.smart4it.vacancycollector.vacancies.vacancy.Vacancy;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "vacancy_snapshot")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Snapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_task_id")
    private Task task;
}