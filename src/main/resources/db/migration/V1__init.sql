CREATE TABLE users
(
    id                  BIGSERIAL PRIMARY KEY,
    username            VARCHAR(30) NOT NULL UNIQUE,
    password            VARCHAR(80) NOT NULL,
    email               VARCHAR(50) NOT NULL UNIQUE,
    created_at          TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated             TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    registration_status VARCHAR(30) NOT NULL,
    last_login          TIMESTAMP,
    deleted             BOOLEAN     NOT NULL DEFAULT false
);

CREATE TABLE IF NOT EXISTS posts
(
    id         BIGSERIAL PRIMARY KEY,
    title      VARCHAR(255) NOT NULL,
    content    TEXT         NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted    BOOLEAN      NOT NULL DEFAULT false,
    likes      INTEGER      NOT NULL DEFAULT 0,
    UNIQUE (title)
);

INSERT INTO users (username, password, email, created_at, updated, registration_status, last_login, deleted)
VALUES ('john_doe', 'password_1', 'john@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP,
        false),
       ('dwayne_carter', 'password_2', 'dwayne_carter@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE',
        CURRENT_TIMESTAMP, false),
       ('test_user', 'password_3', 'test@gmail.com', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'ACTIVE', CURRENT_TIMESTAMP,
        false);

INSERT INTO posts (title, content, created_at, updated, deleted, likes)
VALUES ('First Post', 'This is content of the first post', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, 11),
       ('Second Post', 'This is content of the second post', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, false, 2);