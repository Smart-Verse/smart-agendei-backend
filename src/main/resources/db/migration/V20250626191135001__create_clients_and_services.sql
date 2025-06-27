CREATE TABLE IF NOT EXISTS client (

    id uuid,
    name varchar,
    birth_date date,
    cpf varchar,
    phone varchar,
    email varchar,
    status varchar
);

CREATE TABLE IF NOT EXISTS service (

    id uuid,
    name varchar,
    description varchar,
    execution_time integer,
    price numeric,
    status varchar
);

ALTER TABLE client  ADD CONSTRAINT pk_client  PRIMARY KEY (id);
ALTER TABLE service  ADD CONSTRAINT pk_service  PRIMARY KEY (id);