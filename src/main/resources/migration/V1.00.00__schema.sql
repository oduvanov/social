CREATE TABLE IF NOT EXISTS user_profile
(
    id uuid NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    first_name  VARCHAR(255) NOT NULL,
    second_name VARCHAR(255) NOT NULL,
    birthday DATE,
    biography VARCHAR(255),
    city VARCHAR(255)
);
