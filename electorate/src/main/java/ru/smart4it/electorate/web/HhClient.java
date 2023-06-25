package ru.smart4it.electorate.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.smart4it.electorate.web.dto.VacanciesDto;
import ru.smart4it.electorate.web.dto.FoundDto;

@FeignClient(value = "hh-client", url = "https://api.hh.ru")
public interface HhClient {

    @GetMapping(path = "/vacancies?per_page=100")
    VacanciesDto vacancies(@RequestParam String text, @RequestParam int page);

    @GetMapping(path = "/vacancies")
    FoundDto vacanciesSize(@RequestParam String text);
}
