CREATE TABLE task_instance
(
    id              UUID PRIMARY KEY            NOT NULL,
    title           VARCHAR(250)                NOT NULL,
    specification   JSON                        NOT NULL,
    start_time      TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    completion_time TIMESTAMP(0) WITH TIME ZONE NOT NULL,
    completed       BOOLEAN                     NOT NULL DEFAULT FALSE
);

COMMENT ON TABLE task_instance IS 'Экземпляр задачи';
COMMENT ON COLUMN task_instance.id IS 'Идентификатор задачи';
COMMENT ON COLUMN task_instance.title IS 'Заголовок задачи';
COMMENT ON COLUMN task_instance.specification IS 'Спецификация задачи';
COMMENT ON COLUMN task_instance.start_time IS 'Время запуска задачи';
COMMENT ON COLUMN task_instance.completion_time IS 'Время завершения задачи';
COMMENT ON COLUMN task_instance.completed IS 'Признак завершения задачи';