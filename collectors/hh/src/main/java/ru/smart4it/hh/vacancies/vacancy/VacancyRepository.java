package ru.smart4it.hh.vacancies.vacancy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VacancyRepository extends JpaRepository<Vacancy, UUID> {
}