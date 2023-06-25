package ru.smart4it.hh.vacancies.snapshot;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SnapshotRepository extends JpaRepository<Snapshot, UUID> {

    void deleteAllByTaskId(UUID vacancyTaskId);

    @EntityGraph(attributePaths = {"vacancy"})
    List<Snapshot> findAllByTaskId(UUID vacancyTaskId);
}
