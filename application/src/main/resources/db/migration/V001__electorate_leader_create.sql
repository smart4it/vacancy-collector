CREATE TABLE leader_instance
(
    id             INTEGER PRIMARY KEY,
    instance_id    UUID UNIQUE,
    last_heartbeat BIGINT NOT NULL
);