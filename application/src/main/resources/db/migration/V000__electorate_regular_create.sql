CREATE TABLE regular_instance
(
    instance_id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    last_heartbeat BIGINT NOT NULL
);

COMMENT ON TABLE regular_instance IS 'Запущенные экземпляры приложения';
COMMENT ON COLUMN regular_instance.instance_id IS 'Идентификатор экземпляра';
COMMENT ON COLUMN regular_instance.last_heartbeat IS 'Метка времени последней активности';