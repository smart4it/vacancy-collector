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

    private final RegularInstanceRepository regularInstanceRepository;

    private final LeaderInstanceRepository leaderInstanceRepository;

    @Value("${task-manager.regular.heartbeat.timeout}")
    private Long regularTimeout;

    @Value("${task-manager.leader.heartbeat.timeout}")
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
    @Scheduled(fixedDelayString = "${task-manager.regular.heartbeat.interval}")
    public void updateRegularInstanceHeartbeat() {
        log.debug("updateRegularInstanceHeartbeat started");
        RegularInstance regularInstance = new RegularInstance();
        regularInstance.setInstanceId(instanceId);
        regularInstance.setLastHeartbeat(LocalDateTime.now().toEpochSecond(UTC));
        regularInstance = regularInstanceRepository.save(regularInstance);
        instanceId = regularInstance.getInstanceId();
        log.debug("updateRegularInstanceHeartbeat completed");
    }

    @Scheduled(fixedDelayString = "${task-manager.regular.heartbeat.timeout}")
    public void removeExpiredRegularInstances() {
        long timeout = LocalDateTime.now().toEpochSecond(UTC) - regularTimeout / 1000;
        regularInstanceRepository.deleteAllByLastHeartbeatIsBefore(timeout);

    }

    /**
     * Процедура выбора лидера.
     * <p>
     * Выполняет регистрацию лидера, если активный лидер отсутствует.
     * Активным считается лидер, для которого выполнялось обновление heartbeat
     * не позднее заданного промежутка времени.
     */
    @Scheduled(fixedDelayString = "${task-manager.leader.heartbeat.interval}")
    public void updateLeaderHeartbeat() {
        if (instanceId == null) {
            return;
        }
        leaderInstanceRepository.updateHeartBeat(instanceId, LocalDateTime.now().toEpochSecond(UTC));
    }


    @Scheduled(fixedDelayString = "${task-manager.leader.heartbeat.timeout}")
    @Transactional
    public void determineLeader() {
        if (instanceId == null) {
            return;
        }
        long timeout = LocalDateTime.now().toEpochSecond(UTC) - leaderTimeout / 1000;
        Optional<LeaderInstance> activeLeader = leaderInstanceRepository.findActiveLeader(timeout);
        if (activeLeader.isPresent()) {
            return;
        }
        Optional<LeaderInstance> activeLeaderWithBlocking = leaderInstanceRepository.findActiveLeaderAndLock(timeout);
        if (activeLeaderWithBlocking.isEmpty()) {
            LeaderInstance leader = new LeaderInstance(1, instanceId, LocalDateTime.now().toEpochSecond(UTC));
            leaderInstanceRepository.save(leader);
        }
    }
}
