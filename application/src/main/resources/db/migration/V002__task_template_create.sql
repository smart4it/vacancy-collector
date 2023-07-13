CREATE TABLE task_template
(
    id              UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    title           VARCHAR(250) NOT NULL,
    type            VARCHAR(25)  NOT NULL,
    specification   JSON         NOT NULL,
    cron_expression VARCHAR(100) NOT NULL,
    last_execution  TIMESTAMP    NOT NULL DEFAULT '1970-01-01',
    deleted         BOOLEAN      NOT NULL DEFAULT false
);

COMMENT ON TABLE task_template IS 'Шаблон задачи';
COMMENT ON COLUMN task_template.id IS 'Идентификатор шаблона';
COMMENT ON COLUMN task_template.title IS 'Заголовок задачи';
COMMENT ON COLUMN task_template.type IS 'Тип задачи';
COMMENT ON COLUMN task_template.specification IS 'Спецификация задачи';
COMMENT ON COLUMN task_template.cron_expression IS 'Расписание в виде cron выражения';
COMMENT ON COLUMN task_template.last_execution IS 'Время последнего запуска';
COMMENT ON COLUMN task_template.deleted IS 'Метка удаления';