package ru.smart4it.taskmanager.electorate.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class RegularInstance {

    @Id
    private UUID uuid;
}
