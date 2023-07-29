CREATE TABLE hh_vacancy
(
    data_id VARCHAR(250) PRIMARY KEY NOT NULL,
    data    JSON                     NOT NULL
);

COMMENT ON TABLE hh_vacancy IS 'Данные';
COMMENT ON COLUMN hh_vacancy.data IS 'Данные';