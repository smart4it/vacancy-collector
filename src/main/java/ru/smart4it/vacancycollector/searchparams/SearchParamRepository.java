package ru.smart4it.vacancycollector.searchparams;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SearchParamRepository extends JpaRepository<SearchParam, UUID> {
}
