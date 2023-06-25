package ru.smart4it.electorate.web.dto;

import java.util.List;
import java.util.Map;

public record VacanciesDto(List<Map<String, Object>> items, Integer found, Integer page) {
}
