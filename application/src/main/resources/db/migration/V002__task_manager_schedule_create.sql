CREATE TABLE taskTemplate
(
    id              UUID PRIMARY KEY            NOT NULL DEFAULT gen_random_uuid(),
    title           VARCHAR(250)                NOT NULL,
    type            VARCHAR(50)                 NOT NULL,
    specification   JSON                        NOT NULL,
    cron_expression VARCHAR(100)                NOT NULL,
    last_execution  TIMESTAMP(0) WITH TIME ZONE NOT NULL DEFAULT '1970-01-01',
    deleted         BOOLEAN                     NOT NULL DEFAULT false
);

COMMENT ON TABLE taskTemplate IS 'Расписание запуска задач';
COMMENT ON COLUMN taskTemplate.id IS 'Идентификатор шаблона задачи';
COMMENT ON COLUMN taskTemplate.title IS 'Заголовок шаблона задачи';
COMMENT ON COLUMN taskTemplate.type IS 'Тип задачи';
COMMENT ON COLUMN taskTemplate.specification IS 'Спецификация задачи';
COMMENT ON COLUMN taskTemplate.cron_expression IS 'Расписание в виде cron выражения';
COMMENT ON COLUMN taskTemplate.last_execution IS 'Время последнего запуска';
COMMENT ON COLUMN taskTemplate.deleted IS 'Признак удаления';