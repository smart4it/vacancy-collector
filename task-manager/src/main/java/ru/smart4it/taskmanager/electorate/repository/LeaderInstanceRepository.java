package ru.smart4it.taskmanager.electorate.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.smart4it.taskmanager.electorate.model.LeaderInstance;

import java.util.Optional;
import java.util.UUID;

import static jakarta.persistence.LockModeType.PESSIMISTIC_READ;

public interface LeaderInstanceRepository extends JpaRepository<LeaderInstance, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE LeaderInstance l SET l.lastHeartbeat = :lastHeartbeat WHERE l.instanceId = :instanceId and l.id = 1")
    void updateHeartBeat(UUID instanceId, Long lastHeartbeat);

    @Query("SELECT l FROM LeaderInstance l WHERE l.lastHeartbeat >= :timeout and l.id = 1")
    Optional<LeaderInstance> findActiveLeader(long timeout);

    @Lock(PESSIMISTIC_READ)
    @Query("SELECT l FROM LeaderInstance l WHERE l.lastHeartbeat >= :timeout and l.id = 1")
    Optional<LeaderInstance> findActiveLeaderAndLock(long timeout);
}
