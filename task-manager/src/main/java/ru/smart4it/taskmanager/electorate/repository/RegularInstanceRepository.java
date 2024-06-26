package ru.smart4it.taskmanager.electorate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.smart4it.taskmanager.electorate.model.RegularInstance;

import java.util.UUID;

public interface RegularInstanceRepository extends JpaRepository<RegularInstance, UUID> {

    @Transactional
    void deleteAllByLastHeartbeatIsBefore(Long lastHeartbeat);

}
