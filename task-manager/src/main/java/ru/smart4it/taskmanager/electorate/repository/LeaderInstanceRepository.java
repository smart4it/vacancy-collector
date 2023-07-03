package ru.smart4it.taskmanager.electorate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.smart4it.taskmanager.electorate.model.LeaderInstance;

import java.util.UUID;

public interface LeaderInstanceRepository extends JpaRepository<LeaderInstance, UUID> {
}
