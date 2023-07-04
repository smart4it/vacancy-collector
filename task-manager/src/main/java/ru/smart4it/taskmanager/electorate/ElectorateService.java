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
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

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

    @Scheduled(fixedDelayString = "${task-manager.regular.heartbeat.interval}")
    public void updateRegularInstanceHeartBit() {
        log.debug("updateRegularHeartBit started");
        RegularInstance regularInstance = new RegularInstance();
        if (instanceId != null) {
            regularInstance.setInstanceId(instanceId);
        }
        regularInstance.setLastHeartbeat(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        regularInstance = regularInstanceRepository.save(regularInstance);
        instanceId = regularInstance.getInstanceId();
        log.debug("updateRegularHeartBit completed");
    }

    @Scheduled(fixedDelayString = "${task-manager.regular.heartbeat.timeout}")
    public void removeExpiredRegularInstances() {
        long timeout = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - regularTimeout / 1000;
        regularInstanceRepository.deleteAllByLastHeartbeatIsBefore(timeout);

    }

    /**
     * Процедура выбора лидера.
     * <p>
     * Выполняет регистрацию лидера, если активный лидер отсутствует.
     * Активным считается лидер, для которого выполнялось обновление heartbeat
     * не позднее заданного промежутка времени.
     */
    @Scheduled(fixedDelayString = "${task-manager.leader.heartbeat.timeout}")
    @Transactional
    public void determineLeader() {
//        List<LeaderInstance> leaders = leaderInstanceRepository.findAll();
//        if (leaders.size() == 1) {
//            return;
//        }
//        if (leaders.size() > 1) {
//            leaderInstanceRepository.deleteAll();
//        }
//        LeaderInstance leaderInstance = leaders.get(0);
//        if (!instanceId.equals(leaderInstance.getUuid())) {
//            return;
//        }
//        leaderInstanceRepository.save(leaderInstance);

    }

    @Scheduled(fixedDelayString = "${task-manager.leader.heartbeat.interval}")
    @Transactional
    public void updateLeaderHeartbeat() {
//        List<LeaderInstance> leaders = leaderInstanceRepository.findAll();
//        if (leaders.size() == 1 && instanceId != null && instanceId.equals(leaders.get(0).getUuid())) {
//            leaderInstanceRepository.save(leaders.get(0));
//        }
    }
}
