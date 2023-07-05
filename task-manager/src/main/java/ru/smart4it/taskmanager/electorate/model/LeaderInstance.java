package ru.smart4it.taskmanager.electorate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaderInstance {

    @Id
    private Integer id;

    private UUID instanceId;

    @Column(name = "last_heartbeat")
    private Long lastHeartbeat;
}
