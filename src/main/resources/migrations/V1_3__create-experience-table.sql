CREATE TABLE company (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL UNIQUE ,
    description TEXT DEFAULT '',
    photo_id integer REFERENCES files(id)
);

CREATE TABLE skill (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL UNIQUE ,
    category VARCHAR (100) NOT NULL
);

CREATE TABLE project (
    id SERIAL PRIMARY KEY,
    name VARCHAR (100) NOT NULL ,
    skill_ids INTEGER []
);

CREATE TABLE work_experience (
    id SERIAL PRIMARY KEY,
    start BIGINT NOT NULL ,
    "end" BIGINT NOT NULL ,
    user_id INTEGER REFERENCES "user"(id),
    company_id INTEGER REFERENCES company(id),
    project_id INTEGER REFERENCES project(id)
);
