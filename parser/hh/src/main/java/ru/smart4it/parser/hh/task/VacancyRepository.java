package ru.smart4it.parser.hh.task;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VacancyRepository extends JpaRepository<HhVacancyEntity, UUID> {
}
