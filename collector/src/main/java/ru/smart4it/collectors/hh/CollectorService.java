package ru.smart4it.collectors.hh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.smart4it.collectors.hh.event.Task;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CollectorService {

//    private final VacancyRepository vacancyRepository;
//    private final TaskRepository taskRepository;
//    private final SubtaskRepository subtaskRepository;
//    private final SnapshotRepository snapshotRepository;
//    private final HhClient hhClient;
//    private final TransactionTemplate transactionTemplate;

    @KafkaListener(topics = "test")
    public void createTaskToSaveVacancies(String message) {
        Task task;
        try {
            task = new ObjectMapper().readValue(message, Task.class);
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = task.requests().get(0).url();
            UUID id = task.id();
            ResponseEntity<String> response
                    = restTemplate.getForEntity(fooResourceUrl, String.class);
            System.out.println(response);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
        }

    }

//    public void createTaskToSaveVacancies() {
//        LocalDateTime created = LocalDateTime.now();
//        createTask(created);
//    }

//    private void createTask(LocalDateTime created) {
//        for (var parameter : searchParamsService.getParameters()) {
//            FoundDto foundDto = hhClient.vacanciesSize(parameter.text());
//            Task task = new Task(UUID.randomUUID(), parameter.text(), created, foundDto.found().intValue(),
//                                 Status.IN_PROGRESS);
//            taskRepository.save(task);
//            createSubTask(task);
//        }
//    }
//    private void createSubTask(Task task) {
//        int page = 0;
//        while (page < 20) {
//            subtaskRepository.save(new Subtask(UUID.randomUUID(), page++, false, task));
//        }
//        saveVacancy();
//    }
//
//    @Scheduled(cron = "*/3 * * * * *")
//    @Async
//    public void saveVacancy() {
//        log.info("HhScheduler.saveVacancy() started");
//        taskRepository.findFirstByStatus(Status.IN_PROGRESS).ifPresent(task -> {
//            //Todo findByVacancyTaskId() с писсиместической блокировкой
//            transactionTemplate.execute((i) -> {
//                subtaskRepository.findFirstByDoneIsFalseAndTaskId(task.getId())
//                        .ifPresentOrElse(this::performSubtask, () -> executionControl(task));
//                return this;
//            });
//        });
//        log.info("HhScheduler.saveVacancy() completed");
//    }
//
//    private void performSubtask(Subtask subtask) {
//        VacanciesDto json = hhClient.vacancies(subtask.getTask().getSearchParams(), subtask.getPage());
//        List<Vacancy> vacancies = vacanciesByVacanciesDto(json, subtask.getTask().getStart());
//        vacancyRepository.saveAll(vacancies);
//        saveVacancySnapshot(vacancies, subtask.getTask());
//        subtask.setDone(true);
//        subtaskRepository.save(subtask);
//    }
//
//    private List<Vacancy> vacanciesByVacanciesDto(VacanciesDto dto, LocalDateTime created) {
//        List<Vacancy> vacancies = new ArrayList<>();
//        for (var json : dto.items()) {
//            String vacancyId = json.get("id").toString();
//            Vacancy vacancy = new Vacancy(UUID.randomUUID(), vacancyId, json, created);
//            vacancies.add(vacancy);
//        }
//        return vacancies;
//    }
//
//    private void saveVacancySnapshot(List<Vacancy> vacancies, Task vacancyTask) {
//        for (var vacancy : vacancies) {
//           Snapshot snapshot = Snapshot.builder()
//                    .vacancy(vacancy)
//                    .task(vacancyTask)
//                    .date(vacancy.getCreated())
//                    .build();
//            snapshotRepository.save(snapshot);
//        }
//    }
//
//    private void executionControl(Task task) {
//        List<Subtask> subtask = subtaskRepository.findAllByDoneIsTrueAndTaskId(task.getId());
//        task.setStatus(isCompleted(subtask, task)
//                    ? Status.COMPLETED : Status.ERROR);
//
//            deleteTaskAndSubTask(task);
//            taskRepository.save(task);
//    }
//
//    private Boolean isCompleted(List<Subtask> subtasks, Task task) {
//        var allByTask = subtaskRepository.findAllByTaskId(task.getId());
//        LocalDateTime now = LocalDateTime.now();
//        return subtasks.stream()
//                .filter(Subtask::isDone)
//                .filter(i -> i.getTask().getStart().isAfter(now.minusHours(1)))
//                .count() == allByTask.size();
//    }
//
//    private void deleteTaskAndSubTask(Task task) {
//        if (task.getStatus().equals(Status.ERROR)) {
//            subtaskRepository.deleteAllByTaskId(task.getId());
//            List<Vacancy> vacancies = snapshotRepository.findAllByTaskId(task.getId())
//                    .stream()
//                    .map(Snapshot::getVacancy)
//                    .toList();
//            vacancyRepository.deleteAll(vacancies);
//            snapshotRepository.deleteAllByTaskId(task.getId());
//        }
//    }
}