package ru.smart4it.taskmanager.electorate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class LeaderInstance {

    @Id
    private final UUID uuid;

    @Column(name = "last_heartbeat")
    private Long lastHeartbeat;
}
