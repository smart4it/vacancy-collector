package ru.smart4it.collector.hh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.smart4it.collector.event.Task;
import ru.smart4it.collector.task.HhVacancyEntity;
import ru.smart4it.collector.task.TaskEntity;
import ru.smart4it.collector.task.TaskHhVacancyEntity;
import ru.smart4it.collector.task.TaskHhVacancyRepository;
import ru.smart4it.collector.task.TaskRepository;
import ru.smart4it.collector.task.VacancyRepository;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhCollector {

    private final TaskRepository taskRepository;

    private final VacancyRepository vacancyRepository;

    private final TaskHhVacancyRepository taskHhVacancyRepository;

    //@KafkaListener(topics = "hh_vacancies")
    public void createTaskToSaveVacancies(String message) {
        Task task = createTask(message);
        TaskEntity taskEntity = new TaskEntity(task.id(), task.title(), message, OffsetDateTime.now(), OffsetDateTime.now(), false);
        taskRepository.save(taskEntity);
        List<String> taskData = getTaskData(task);
        saveData(taskData, taskEntity);
    }

    private Task createTask(String message) {
        Task task;
        try {
            task = new ObjectMapper().readValue(message, Task.class);

        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException();
        }
        return task;
    }

    public List<String> getTaskData(Task task) {
        log.info("createTaskToSaveVacancies");
        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = task.requests().get(0).url();
        Long pageSize = task.pagination().pageSize();
        Long total = task.pagination().total();
        String param = task.requests().get(0).queryParams().get(0).param();
        String value = task.requests().get(0).queryParams().get(0).value();
        log.info("createTaskToSaveVacancies: start={}, end={}", 0, total / pageSize);
        List<String> data = new ArrayList<>();
        for (int i = 0; i < total / pageSize; i++) {

            URI uri = UriComponentsBuilder.fromHttpUrl(resourceUrl)
                    .queryParam(param, value)
                    .queryParam("page", i)
                    .queryParam("per_page", pageSize)
                    .build()
                    .encode()
                    .toUri();
            ResponseEntity<String> response
                    = restTemplate.getForEntity(uri, String.class);
            String body = response.getBody();
            data.add(body);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    @Transactional
    public void saveData(List<String> taskData, TaskEntity task) {
        log.info("saveData");
        for (String dataItem : taskData) {
            VacanciesDto vacancies;
            List<HhVacancyEntity> hhVacancyEntities = new ArrayList<>();
            List<TaskHhVacancyEntity> taskVacancies = new ArrayList<>();
            try {
                vacancies = new ObjectMapper().readValue(dataItem, VacanciesDto.class);

                for (Map<String, Object> vacancy : vacancies.items()) {
                    HhVacancyEntity hhVacancyEntity = new HhVacancyEntity();
                    hhVacancyEntity.setId(vacancy.get("id").toString());
                    hhVacancyEntities.add(hhVacancyEntity);

                    taskVacancies.add(
                            new TaskHhVacancyEntity(new TaskHhVacancyEntity.TaskHhVacancyEntityId(
                                    task.getId(),
                                    hhVacancyEntity.getId()
                            ))
                    );
                }
                vacancyRepository.saveAll(hhVacancyEntities);
                taskHhVacancyRepository.saveAll(taskVacancies);

            } catch (JsonProcessingException e) {
                log.error(e.toString());
            }
        }
    }

    @Scheduled(fixedDelay = 1000)
    @Transactional
    public void enrich() {
        log.info("enrich and save started");
        Optional<HhVacancyEntity> optVacancy = vacancyRepository.findFirstByDataIsNull();
        if (optVacancy.isEmpty()) {
            return;
        }
        HhVacancyEntity vacancy = optVacancy.get();
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromHttpUrl("https://api.hh.ru/vacancies/" + vacancy.getId())
                .build()
                .encode()
                .toUri();
        ResponseEntity<String> response
                = restTemplate.getForEntity(uri, String.class);
        vacancy.setData(response.getBody());
        try {
            vacancyRepository.save(vacancy);
        } catch (Throwable e) {
            log.error(e.getMessage());
        }

        log.info("enrich and save finished");
    }
}