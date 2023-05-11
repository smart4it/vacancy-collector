package ru.smart4it.vacancycollector.vacancies.subtask;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubtaskRepository extends JpaRepository<Subtask, UUID> {

    List<Subtask> findAllByDoneIsTrueAndTaskId(UUID vacancyTaskId);

    void deleteAllByTaskId(UUID vacancyTaskId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Subtask> findFirstByDoneIsFalseAndTaskId(UUID id);

    List<Subtask> findAllByTaskId(UUID vacancyTaskId);
}

