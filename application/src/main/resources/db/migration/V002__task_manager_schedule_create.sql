CREATE TABLE schedule
(
    id              UUID PRIMARY KEY            NOT NULL DEFAULT gen_random_uuid(),
    name            VARCHAR(250)                NOT NULL,
    type            VARCHAR(50)                 NOT NULL,
    task_template   JSON                        NOT NULL,
    cron_expression VARCHAR(100)                NOT NULL,
    execution_time  TIMESTAMP(0) WITH TIME ZONE NOT NULL DEFAULT '1970-01-01',
    deleted         BOOLEAN                     NOT NULL DEFAULT false
);

COMMENT ON TABLE schedule IS 'Расписание запуска задач';
COMMENT ON COLUMN schedule.id IS 'Идентификатор события';
COMMENT ON COLUMN schedule.name IS 'Наименование события';
COMMENT ON COLUMN schedule.type IS 'Тип события';
COMMENT ON COLUMN schedule.task_template IS 'Шаблон задачи';
COMMENT ON COLUMN schedule.cron_expression IS 'Расписание в виде cron выражения';
COMMENT ON COLUMN schedule.execution_time IS 'Время последнего запуска';
COMMENT ON COLUMN schedule.deleted IS 'Признак удаленного события';