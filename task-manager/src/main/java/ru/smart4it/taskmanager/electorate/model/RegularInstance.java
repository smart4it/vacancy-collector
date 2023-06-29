package ru.smart4it.taskmanager.electorate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class RegularInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID instance_id;

    @Column(name = "last_heartbeat")
    private Long lastHeartbeat;
}
