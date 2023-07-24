package ru.smart4it.parser.hh.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record VacanciesDto(List<Map<String, Object>> items,
                           Integer found,
                           Integer pages,
                           Integer perPage,
                           Integer page,
                           String clusters,
                           String arguments,
                           String alternateUrl) {
}
