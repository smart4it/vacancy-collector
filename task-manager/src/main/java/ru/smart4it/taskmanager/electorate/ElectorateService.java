package ru.smart4it.taskmanager.electorate;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.smart4it.taskmanager.electorate.model.RegularInstance;
import ru.smart4it.taskmanager.electorate.repository.RegularInstanceRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElectorateService {

    private final RegularInstanceRepository regularInstanceRepository;

    @Value("${task-manager.regular.heartbeat.timeout}")
    private Long regularHeartbeatTimeout;

    private UUID instanceId;

    @Scheduled(fixedDelayString = "${task-manager.regular.heartbeat.interval}")
    @Transactional
    public void updateRegularHeartBit() {
        log.debug("updateRegularHeartBit started");
        if (instanceId == null) {
            RegularInstance regularInstance = new RegularInstance();
            regularInstance.setLastHeartbeat(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            regularInstanceRepository.save(regularInstance);
            instanceId = regularInstance.getInstance_id();
        }
        Optional<RegularInstance> optionalRegularInstance = regularInstanceRepository.findById(instanceId);
        if (optionalRegularInstance.isEmpty()) {
            RegularInstance regularInstance = new RegularInstance();
            regularInstance.setLastHeartbeat(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            regularInstanceRepository.save(regularInstance);
            instanceId = regularInstance.getInstance_id();
        } else {
            RegularInstance regularInstance = optionalRegularInstance.get();
            regularInstance.setLastHeartbeat(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            regularInstanceRepository.save(regularInstance);
        }
        // if heartBit is valid
        // update heart bit for regular instance
        log.debug("updateRegularHeartBit completed");
    }

    @Scheduled(fixedDelayString = "${task-manager.regular.heartbeat.timeout}")
    @Transactional
    public void removeExpiredRegularInstances() {
        long timeout = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) - regularHeartbeatTimeout / 1000;
        regularInstanceRepository.deleteAllByLastHeartbeatIsBefore(timeout);

    }

    private void determineLeader() {

    }

    public RegularInstance getInstance() {
        return regularInstanceRepository.getReferenceById(instanceId);
    }
}
