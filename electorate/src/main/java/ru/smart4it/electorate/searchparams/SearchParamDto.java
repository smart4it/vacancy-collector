package ru.smart4it.electorate.searchparams;

import java.util.UUID;


public record SearchParamDto(UUID id, String text, boolean disabled) {
}
