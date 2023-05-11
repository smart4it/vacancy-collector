package ru.smart4it.vacancycollector.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "vacancy_task")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VacancyTask {

    @Id
    private UUID id;
    @Column(name = "search_parameter", length = 25)
    private String searchParams;
    private LocalDateTime start;
    private Integer count;
    @Enumerated(EnumType.STRING)
    private Status status;

}