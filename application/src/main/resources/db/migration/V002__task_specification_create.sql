CREATE TABLE leader_instance
(
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    specification  json,
    instance_id    UUID UNIQUE,
    last_heartbeat BIGINT NOT NULL
);