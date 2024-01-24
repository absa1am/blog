CREATE SEQUENCE categories_seq START 1;

CREATE TABLE IF NOT EXISTS categories (
    id INTEGER DEFAULT nextval('categories_seq') PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

CREATE SEQUENCE posts_seq START 1;

CREATE TABLE If NOT EXISTS posts (
    id INTEGER DEFAULT nextval('posts_seq') PRIMARY KEY,
    title VARCHAR(255),
    description VARCHAR(2500),
    image VARCHAR(255),
    category_id INTEGER,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);