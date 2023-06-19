package ru.smart4it.hhru.vacancies.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smart4it.hhru.vacancies.Status;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    Optional<Task> findFirstByStatus(Status status);
}
