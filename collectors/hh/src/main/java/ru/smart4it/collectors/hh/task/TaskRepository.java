package ru.smart4it.collectors.hh.task;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smart4it.collectors.hh.Status;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    Optional<Task> findFirstByStatus(Status status);
}
