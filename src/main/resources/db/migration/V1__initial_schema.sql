Create Table app_user(
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL DEFAULT 'USER'
);
CREATE TABLE category(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);
CREATE TABLE task(
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    is_done BOOLEAN,
    category_id BIGINT REFERENCES category(id)
);