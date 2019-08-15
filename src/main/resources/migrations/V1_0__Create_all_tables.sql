CREATE TABLE files (
    id SERIAL PRIMARY KEY,
    original_filename varchar(256) NOT NULL,
    dir varchar(64) NOT NULL,
    stored_filename varchar(64) NOT NULL,
    datetime timestamp without time zone NOT NULL DEFAULT current_timestamp
);

CREATE TABLE user_role (
    id SERIAL PRIMARY KEY,
    name varchar(255) UNIQUE NOT NULL,
    application_role varchar(255)[] NOT NULL
);

CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    last_name varchar(255),
    first_name varchar(255),
    middle_name varchar(255),
    mobile_phone varchar(255) UNIQUE DEFAULT '',
    role_id integer REFERENCES user_role(id) NOT NULL,
    photo_id integer REFERENCES files(id),
    password_hash varchar(255) NOT NULL,
    status VARCHAR(50) NOT NULL
);
