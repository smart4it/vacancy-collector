CREATE TABLE regular_instance
(
    instance_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    last_heartbeat BIGINT NOT NULL
);