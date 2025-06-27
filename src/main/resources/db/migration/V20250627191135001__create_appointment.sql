CREATE TABLE IF NOT EXISTS appointment(

    id uuid,
    client uuid,
    service uuid,
    start_date timestamp,
    end_date timestamp,
    description varchar,
    user_name uuid,
    cell_color varchar,
    status varchar,
    discount numeric(18,2),
    total numeric(18,2)
);

CREATE TABLE IF NOT EXISTS extras(

    id uuid,
    service uuid,
    appointment uuid
);

ALTER TABLE appointment  ADD CONSTRAINT pk_appointment  PRIMARY KEY (id);
ALTER TABLE extras  ADD CONSTRAINT pk_extras  PRIMARY KEY (id);
-- Fks

ALTER TABLE appointment ADD CONSTRAINT fk_appointment_client_client FOREIGN KEY (client) REFERENCES client(id);
ALTER TABLE appointment ADD CONSTRAINT fk_appointment_service_service FOREIGN KEY (service) REFERENCES service(id);
ALTER TABLE appointment ADD CONSTRAINT fk_appointment_user_configuration_user_name FOREIGN KEY (user_name) REFERENCES user_configuration(id);
ALTER TABLE extras ADD CONSTRAINT fk_extras_service_service FOREIGN KEY (service) REFERENCES service(id);
ALTER TABLE extras ADD CONSTRAINT fk_extras_appointment_appointment FOREIGN KEY (appointment) REFERENCES appointment(id);
--RelationShips