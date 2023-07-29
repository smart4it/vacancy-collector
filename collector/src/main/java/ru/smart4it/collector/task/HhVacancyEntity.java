package ru.smart4it.collector.task;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@Table(name = "hh_vacancy")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HhVacancyEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Type(JsonType.class)
    private String data;
}