package ru.smart4it.collectors.hh.subtask;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.smart4it.collectors.hh.task.Task;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "vacancy_subtask")
@AllArgsConstructor
@NoArgsConstructor
public class Subtask {

    @Id
    private UUID id;
    private int page;
    private boolean done;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_task_id")
    private Task task;
}