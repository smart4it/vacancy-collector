package ru.smart4it.electorate.vacancies.subtask;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubtaskRepository extends JpaRepository<Subtask, UUID> {

    List<Subtask> findAllByDoneIsTrueAndTaskId(UUID vacancyTaskId);

    void deleteAllByTaskId(UUID vacancyTaskId);
    @Query(value = "SELECT * FROM vacancy_subtask WHERE vacancy_task_id = ?1 AND done IS NOT TRUE limit 1 FOR UPDATE",
            nativeQuery = true)
    Optional<Subtask> findFirstByDoneIsFalseAndTaskId(UUID id);

    List<Subtask> findAllByTaskId(UUID vacancyTaskId);
}