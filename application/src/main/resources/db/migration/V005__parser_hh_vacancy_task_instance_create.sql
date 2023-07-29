CREATE TABLE hh_vacancy_task_instance
(
    task_instance_id UUID REFERENCES task_instance (id)          NOT NULL,
    hh_vacancy_id VARCHAR(25) REFERENCES hh_vacancy (id) NOT NULL
);