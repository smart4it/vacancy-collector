package ru.smart4it.taskmanager.electorate;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.smart4it.taskmanager.electorate.model.RegularInstance;
import ru.smart4it.taskmanager.electorate.repository.RegularInstanceRepository;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ElectorateService {

    private final RegularInstanceRepository regularInstanceRepository;

    private UUID instanceId;

    @EventListener(ApplicationReadyEvent.class)
    public void registerRegularInstance() {
        RegularInstance regularInstance = new RegularInstance();
        regularInstanceRepository.save(regularInstance);
        instanceId = regularInstance.getUuid();
    }

    @Scheduled
    private void updateHeartBit() {
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
