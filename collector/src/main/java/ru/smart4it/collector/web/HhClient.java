package ru.smart4it.collector.web;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.smart4it.collector.web.dto.FoundDto;
import ru.smart4it.collector.web.dto.VacanciesDto;

@FeignClient(value = "hh-client", url = "https://api.hh.ru")
public interface HhClient {

    @GetMapping(path = "/vacancies?per_page=100")
    VacanciesDto vacancies(@RequestParam String text, @RequestParam int page);

    @GetMapping(path = "/vacancies")
    FoundDto vacanciesSize(@RequestParam String text);
}
