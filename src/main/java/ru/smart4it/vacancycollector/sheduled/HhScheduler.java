package ru.smart4it.vacancycollector.sheduled;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.smart4it.vacancycollector.vacancies.VacancyService;

@Slf4j
@Component
@RequiredArgsConstructor
public class HhScheduler {
    private final VacancyService vacancyService;

    @EventListener(ApplicationReadyEvent.class)
    @Scheduled(cron = "0 0 12 * * ?")
    public void updateVacancies() {
        log.info("HhScheduler.updateVacancies() started");
        vacancyService.createTaskToSaveVacancies();
        log.info("HhScheduler.updateVacancies() completed");
    }
}