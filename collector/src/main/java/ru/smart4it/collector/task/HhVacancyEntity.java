package ru.smart4it.collector.task;


import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "hh_vacancy")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HhVacancyEntity {

    @Id
    @Column(name = "data_id")
    private String dataId;

    @ManyToMany
    @JoinTable(name = "hh_vacancy_task_instance",
            joinColumns = {
                    @JoinColumn(name = "hh_vacancy_id", referencedColumnName = "data_id",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "task_instance_id", referencedColumnName = "id",
                            nullable = false, updatable = false)})
    private List<TaskEntity> task;

    @Type(JsonType.class)
    private String data;
}