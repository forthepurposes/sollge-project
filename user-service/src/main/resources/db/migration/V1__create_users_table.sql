CREATE SEQUENCE IF NOT EXISTS users_id_gen START WITH 1 INCREMENT BY 1;

CREATE TABLE users
(
    id         BIGINT       NOT NULL,
    username   VARCHAR(20)  NOT NULL,
    email      VARCHAR(64)  NOT NULL,
    password   VARCHAR(300) NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users
    ADD CONSTRAINT uc_users_username UNIQUE (username);