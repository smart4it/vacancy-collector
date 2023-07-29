CREATE TABLE hh_vacancy_task_instance
(
    task_instance_id UUID REFERENCES task_instance (id)          NOT NULL,
    hh_vacancy_id VARCHAR(250) REFERENCES hh_vacancy (data_id) NOT NULL
);