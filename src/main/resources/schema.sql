CREATE SEQUENCE categories_seq START 1;

CREATE TABLE IF NOT EXISTS categories (
    id INTEGER DEFAULT nextval('categories_seq') PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);