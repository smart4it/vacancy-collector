CREATE TABLE hh_vacancy
(
    data_id VARCHAR(250) PRIMARY KEY NOT NULL,
    task_id UUID NOT NULL,
    data    JSON         NOT NULL
);

COMMENT ON TABLE hh_vacancy IS 'Данные';
COMMENT ON COLUMN hh_vacancy.task_id IS 'Идентификатор задачи';
COMMENT ON COLUMN hh_vacancy.data IS 'Данные';