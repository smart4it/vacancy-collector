package ru.smart4it.taskmanager.electorate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@RequiredArgsConstructor
public class LeaderInstance {

    @Id
    private final UUID uuid;
}
