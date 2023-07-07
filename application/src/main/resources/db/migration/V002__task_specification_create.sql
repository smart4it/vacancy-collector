CREATE TABLE task_specification
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name          VARCHAR(250),
    type          VARCHAR(25),
    specification JSON,
    disabled      BOOLEAN          DEFAULT false,
    deleted       BOOLEAN          DEFAULT false
);