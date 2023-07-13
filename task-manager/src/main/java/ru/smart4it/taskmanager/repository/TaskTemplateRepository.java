package ru.smart4it.taskmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smart4it.taskmanager.model.TaskTemplate;

import java.util.List;
import java.util.UUID;

public interface TaskTemplateRepository extends JpaRepository<TaskTemplate, UUID> {
    List<TaskTemplate> findAllByDeletedIsFalse();
}
