package ru.smart4it.parser.hh.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VacancyRepository extends JpaRepository<HhVacancyEntity, String> {
}