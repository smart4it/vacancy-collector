package ru.smart4it.collector.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskHhVacancyRepository extends JpaRepository<TaskHhVacancyEntity, TaskHhVacancyEntity.TaskHhVacancyEntityId> {
}
