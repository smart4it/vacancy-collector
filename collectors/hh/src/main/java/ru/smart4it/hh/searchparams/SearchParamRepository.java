package ru.smart4it.hh.searchparams;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SearchParamRepository extends JpaRepository<SearchParam, UUID> {
}