package ru.smart4it.taskmanager.electorate;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.smart4it.taskmanager.electorate.model.RegularInstance;
import ru.smart4it.taskmanager.electorate.repository.RegularInstanceRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ElectorateService {

    private final RegularInstanceRepository regularInstanceRepository;

    private UUID instanceId;

    @Scheduled(fixedDelayString = "${task-manager.heartbeat.interval}")
    @Transactional
    public void updateHeartBit() {
        if (instanceId == null) {
            RegularInstance regularInstance = new RegularInstance();
            regularInstanceRepository.save(regularInstance);
            instanceId = regularInstance.getUuid();
        }
        Optional<RegularInstance> optionalRegularInstance = regularInstanceRepository.findById(instanceId);
        if (optionalRegularInstance.isEmpty()) {
            RegularInstance regularInstance = new RegularInstance();
            regularInstanceRepository.save(regularInstance);
            instanceId = regularInstance.getUuid();
        } else {
            RegularInstance regularInstance = optionalRegularInstance.get();
            regularInstanceRepository.save(regularInstance);
        }
        // if heartBit is valid
        // update heart bit for regular instance
    }

    @Scheduled
    private void determineLeader() {

    }

    public RegularInstance getInstance() {
        return regularInstanceRepository.getReferenceById(instanceId);
    }
}
