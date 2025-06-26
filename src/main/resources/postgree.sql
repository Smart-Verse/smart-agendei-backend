--Entities

CREATE TABLE IF NOT EXISTS user_configuration(
    
  id uuid,
  name varchar,
  user_photo varchar,
  theme varchar,
  lang varchar,
  email varchar,
  hash uuid
);

CREATE TABLE IF NOT EXISTS user_confirmation(
    
  id uuid,
  user_id uuid,
  hash varchar
);

-- PKs

ALTER TABLE user_configuration  ADD CONSTRAINT pk_user_configuration  PRIMARY KEY (id);
ALTER TABLE user_confirmation  ADD CONSTRAINT pk_user_confirmation  PRIMARY KEY (id);
-- Fks

--RelationShips

