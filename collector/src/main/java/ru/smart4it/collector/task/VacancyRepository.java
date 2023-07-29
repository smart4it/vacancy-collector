package ru.smart4it.collector.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VacancyRepository extends JpaRepository<HhVacancyEntity, String> {

    @Query(value = "SELECT * FROM hh_vacancy hv WHERE hv.data is null LIMIT 1 FOR UPDATE SKIP LOCKED", nativeQuery = true)
    Optional<HhVacancyEntity> findFirstByDataIsNull();
}