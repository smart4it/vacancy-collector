package ru.smart4it.vacancycollector.persistence.task;


import jakarta.persistence.*;
import lombok.*;
import ru.smart4it.vacancycollector.persistence.Status;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "vacancy_task")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    private UUID id;
    @Column(name = "search_parameter", length = 25)
    private String searchParams;
    private LocalDateTime start;
    private Integer count;
    @Enumerated(EnumType.STRING)
    private Status status;

}