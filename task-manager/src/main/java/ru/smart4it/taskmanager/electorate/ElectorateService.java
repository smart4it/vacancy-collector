package ru.smart4it.taskmanager.electorate;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    private UUID instanceId;

    @Scheduled(fixedDelayString = "${task-manager.heartbeat.interval}")
    @Transactional
    public void updateHeartBit() {
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
    }

    private void determineLeader() {

    }

    public RegularInstance getInstance() {
        return regularInstanceRepository.getReferenceById(instanceId);
    }
}
