CREATE TABLE service_instance
(
    instance_id    UUID PRIMARY KEY,
    timestamp      BIGINT  NOT NULL,
    last_heartbeat BIGINT  NOT NULL,
    version        BIGINT  NOT NULL,
    is_leader      BOOLEAN NOT NULL
);