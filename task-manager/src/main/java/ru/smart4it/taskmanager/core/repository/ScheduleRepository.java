package ru.smart4it.taskmanager.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smart4it.taskmanager.core.model.ScheduleEvent;

import java.util.List;
import java.util.UUID;

public interface ScheduleRepository extends JpaRepository<ScheduleEvent, UUID> {
    List<ScheduleEvent> findAllByDeletedIsFalse();
}
