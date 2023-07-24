package ru.smart4it.parser.hh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.smart4it.parser.hh.event.Task;
import ru.smart4it.parser.hh.event.VacanciesDto;
import ru.smart4it.parser.hh.task.TaskEntity;
import ru.smart4it.parser.hh.task.TaskRepository;

import java.time.OffsetDateTime;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhParser {

    private final KafkaTemplate<String, String> smart4itKafkaTemplate;

    private final TaskRepository taskRepository;

    @KafkaListener(topics = "test")
    public void createTaskToSaveVacancies(String message) {
        Task task;
        try {
            task = new ObjectMapper().readValue(message, Task.class);
            taskRepository.save(new TaskEntity(task.id(), task.title(), message, OffsetDateTime.now(), OffsetDateTime.now(), false));

            smart4itKafkaTemplate.send("test2", message);

        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }

    }

    @KafkaListener(topics = "test3")
    public void getPartData(String message) {
        VacanciesDto vacancies;
        try {
            vacancies = new ObjectMapper().readValue(message, VacanciesDto.class);
            for (Map<String, Object> vacancy :  vacancies.items()) {
                System.out.println(vacancy);
            }

        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }

    }
}