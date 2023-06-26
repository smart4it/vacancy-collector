CREATE TABLE regular_instance
(
    instance_id    UUID PRIMARY KEY,
    timestamp      BIGINT  NOT NULL,
    last_heartbeat BIGINT  NOT NULL,
    version        BIGINT  NOT NULL
);