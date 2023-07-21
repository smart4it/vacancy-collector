CREATE TABLE task_data
(
    task_id VARCHAR(250) NOT NULL,
    data    JSON         NOT NULL
);

COMMENT ON TABLE task_data IS 'Данные';
COMMENT ON COLUMN task_data.task_id IS 'Идентификатор задачи';
COMMENT ON COLUMN task_data.data IS 'Данные';