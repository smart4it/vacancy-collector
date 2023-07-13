CREATE TABLE leader_instance
(
    id             INTEGER PRIMARY KEY,
    instance_id    UUID UNIQUE,
    last_heartbeat BIGINT NOT NULL
);

COMMENT ON TABLE leader_instance IS 'Текущий лидер';
COMMENT ON COLUMN leader_instance.id IS 'Идентификатор лидера';
COMMENT ON COLUMN leader_instance.instance_id IS 'Идентификатор экземпляра приложения';
COMMENT ON COLUMN leader_instance.last_heartbeat IS 'Метка времени последней активности';