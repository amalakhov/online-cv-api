ALTER TABLE files ADD COLUMN owner_user_id integer REFERENCES "user"(id);
