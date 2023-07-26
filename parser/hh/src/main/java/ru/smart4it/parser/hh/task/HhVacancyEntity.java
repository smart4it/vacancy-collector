package ru.smart4it.parser.hh.task;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "hh_vacancy")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HhVacancyEntity {

    @Id
    private UUID taskId;

    private String dataId;

    @Type(JsonType.class)
    private String data;
}