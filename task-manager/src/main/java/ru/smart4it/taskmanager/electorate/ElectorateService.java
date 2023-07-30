package ru.smart4it.taskmanager.electorate;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.smart4it.taskmanager.electorate.model.LeaderInstance;
import ru.smart4it.taskmanager.electorate.model.RegularInstance;
import ru.smart4it.taskmanager.electorate.repository.LeaderInstanceRepository;
import ru.smart4it.taskmanager.electorate.repository.RegularInstanceRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static java.time.ZoneOffset.UTC;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElectorateService {

    private final static int MS_PER_SECOND = 1000;

    private final RegularInstanceRepository regularInstanceRepository;

    private final LeaderInstanceRepository leaderInstanceRepository;

    @Value("${electorate.regular.heartbeat.timeout}")
    private Long regularTimeout;

    @Value("${electorate.leader.heartbeat.timeout}")
    private Long leaderTimeout;

    private UUID instanceId;

    /**
     * Регистрирует новый экземпляр приложения или обновляет heartbeat существующего.
     * <p>
     * При регистрации экземпляра генерация идентификатора {@code instanceId}
     * выполняется в БД, если {@code instanceId != null}, но экземпляра с таким
     * идентификатором нет в БД, для него будет сгенерирован новый уникальный
     * {@code instanceId}.
     */
    @Scheduled(fixedDelayString = "${electorate.regular.heartbeat.interval}")
    public void updateRegularInstanceHeartbeat() {
        log.debug("updateRegularInstanceHeartbeat started");
        RegularInstance regularInstance = new RegularInstance();
        regularInstance.setInstanceId(instanceId);
        regularInstance.setLastHeartbeat(LocalDateTime.now(UTC).toEpochSecond(UTC));
        regularInstance = regularInstanceRepository.save(regularInstance);
        instanceId = regularInstance.getInstanceId();
        log.debug("updateRegularInstanceHeartbeat completed");
    }

    /**
     * Удаляет неактивные экземпляры сервисов.
     */
    @Scheduled(fixedDelayString = "${electorate.regular.heartbeat.timeout}")
    public void removeExpiredRegularInstances() {
        long timeout = LocalDateTime.now(UTC).toEpochSecond(UTC) - regularTimeout / MS_PER_SECOND;
        regularInstanceRepository.deleteAllByLastHeartbeatIsBefore(timeout);

    }

    /**
     * Выбор лидера.
     * <p>
     * Если активный лидер отсутствует, то устанавливает текущий экземпляр
     * приложения лидером. Активным считается лидер, для которого выполнялось
     * обновление heartbeat не позднее заданного промежутка времени.
     * <p>
     * Стоит отметить, что первый запрос на проверку лидера неблокирующий, что
     * позволяет в большинстве случаев исключить длительное ожидание при
     * одновременных запросах от нескольких сервисов. Если лидер отсутствует,
     * то далее выполняется блокирующий запрос к таблице лидера, чтобы исключить
     * одновременную установку лидера несколькими сервисами.
     * <p>
     * Т.к. в системе не может быть более одного лидера его идентификатор всегда
     * равен 1, что позволяет избавиться от этапа периодической чистки таблицы
     * лидеров (в отличие от обычных экземпляров).
     */
    @Scheduled(fixedDelayString = "${electorate.leader.heartbeat.timeout}")
    @Transactional
    public void determineLeader() {
        if (instanceId == null) {
            return;
        }
        long timeout = LocalDateTime.now(UTC).toEpochSecond(UTC) - leaderTimeout / MS_PER_SECOND;
        Optional<LeaderInstance> activeLeader = leaderInstanceRepository.findActiveLeader(timeout);
        if (activeLeader.isPresent()) {
            return;
        }
        Optional<LeaderInstance> activeLeaderWithBlocking = leaderInstanceRepository.findActiveLeaderAndLock(timeout);
        if (activeLeaderWithBlocking.isEmpty()) {
            LeaderInstance leader = new LeaderInstance(1, instanceId, LocalDateTime.now(UTC).toEpochSecond(UTC));
            leaderInstanceRepository.save(leader);
        }
    }

    /**
     * Обновляет heartbeat лидера.
     * <p>
     * Если текущий экземпляр зарегистрирован, то можно попробовать обновить его
     * heartbeat в таблице лидера. Если текущий экземпляр не является лидером,
     * то обновление не выполниться, т.к. {@code leader.instanceId} !=
     * {@code current.instanceId}.
     */
    @Scheduled(fixedDelayString = "${electorate.leader.heartbeat.interval}")
    public void updateLeaderHeartbeat() {
        if (instanceId == null) {
            return;
        }
        leaderInstanceRepository.updateHeartBeat(instanceId, LocalDateTime.now(UTC).toEpochSecond(UTC));
    }
}