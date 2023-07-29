CREATE TABLE hh_vacancy
(
    id VARCHAR(25) PRIMARY KEY NOT NULL,
    data    JSON
);

COMMENT ON TABLE hh_vacancy IS 'Данные';
COMMENT ON COLUMN hh_vacancy.data IS 'Данные';