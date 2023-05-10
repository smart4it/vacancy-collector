CREATE TABLE IF NOT EXISTS vacancy
(
    id         uuid      DEFAULT gen_random_uuid() PRIMARY KEY,
    vacancy_id VARCHAR(25) NOT NULL,
    text       json,
    created    timestamp DEFAULT (CURRENT_TIMESTAMP at time zone 'utc'),
    updated    timestamp DEFAULT (CURRENT_TIMESTAMP at time zone 'utc')
    );

CREATE TABLE IF NOT EXISTS vacancy_task
(
    id               uuid DEFAULT gen_random_uuid() PRIMARY KEY,
    search_parameter VARCHAR(25) NOT NULL,
    start            TIMESTAMP WITH TIME ZONE,
    count            INT         NOT NULL,
    status           VARCHAR(25) NOT NULL
    );


CREATE TABLE IF NOT EXISTS vacancy_subtask
(
    id              uuid    DEFAULT gen_random_uuid() PRIMARY KEY,
    page            INT NOT NULL,
    done            boolean DEFAULT (false),
    vacancy_task_id uuid REFERENCES vacancy_task (id)
    );

CREATE TABLE IF NOT EXISTS vacancy_snapshot
(
    id              serial PRIMARY KEY,
    date            TIMESTAMP WITH TIME ZONE,
    vacancy_id      uuid REFERENCES vacancy (id),
    vacancy_task_id uuid REFERENCES vacancy_task (id)
    );