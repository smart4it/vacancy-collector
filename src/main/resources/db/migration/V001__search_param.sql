CREATE TABLE IF NOT EXISTS search_parameter
(
    id       uuid                     DEFAULT gen_random_uuid() PRIMARY KEY,
    text     VARCHAR(50)              NOT NULL,
    disabled boolean                  DEFAULT (false),
    created  TIMESTAMP WITH TIME ZONE DEFAULT (CURRENT_TIMESTAMP),
    updated  TIMESTAMP WITH TIME ZONE DEFAULT (CURRENT_TIMESTAMP)
    );