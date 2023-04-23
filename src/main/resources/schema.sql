CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
-- CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS vehicles CASCADE;
DROP TABLE IF EXISTS contractors CASCADE;
DROP TABLE IF EXISTS cells_yard CASCADE;
DROP TABLE IF EXISTS contractors CASCADE;
DROP TABLE IF EXISTS receiving_acts CASCADE;
DROP TABLE IF EXISTS stock_balances CASCADE;
DROP TABLE IF EXISTS vehicle_turnovers CASCADE;

DROP SEQUENCE IF EXISTS vehicles_code_seq CASCADE;
DROP SEQUENCE IF EXISTS contractors_code_seq CASCADE;
DROP SEQUENCE IF EXISTS receiving_acts_doc_number_seq CASCADE;

CREATE TABLE users
(
    id       UUID         NOT NULL DEFAULT gen_random_uuid(),
    name     VARCHAR(150) NOT NULL,
    password VARCHAR(20)  NOT NULL,
    role     VARCHAR(20)  NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS vehicles_code_seq AS INTEGER;

CREATE TABLE vehicles
(
    id          UUID        NOT NULL DEFAULT gen_random_uuid(),
    custom_code INTEGER     NOT NULL DEFAULT nextval('vehicles_code_seq'),
    vin_code    VARCHAR(17) NOT NULL,
    make        VARCHAR(25) NOT NULL,
    model       VARCHAR(25) NOT NULL,
    year        VARCHAR(4)  NOT NULL,
    CONSTRAINT pk_vehicles PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS contractors_code_seq AS INTEGER;

CREATE TABLE contractors
(
    id          UUID         NOT NULL DEFAULT gen_random_uuid(),
    name        VARCHAR(150) NOT NULL,
    custom_code INTEGER      NOT NULL DEFAULT nextval('contractors_code_seq'),
    full_name   VARCHAR(300) NOT NULL,
    edrpou      VARCHAR(10)  NOT NULL,
    CONSTRAINT pk_contractors PRIMARY KEY (id)
);

CREATE TABLE cells_yard
(
    id        UUID        NOT NULL DEFAULT gen_random_uuid(),
    name      VARCHAR(25) NOT NULL,
    folder    BOOLEAN     NOT NULL,
    parent_id UUID,
    CONSTRAINT pk_cells_yard PRIMARY KEY (id),
    CONSTRAINT FK_CELLS_YARD_ON_PARENT FOREIGN KEY (parent_id) REFERENCES cells_yard (id)
);

CREATE SEQUENCE IF NOT EXISTS receiving_acts_doc_number_seq AS INTEGER INCREMENT BY 1;

CREATE TABLE receiving_acts
(
    id            UUID                     NOT NULL DEFAULT gen_random_uuid(),
    doc_number    INTEGER                  NOT NULL DEFAULT nextval('receiving_acts_doc_number_seq'),
    date_time     TIMESTAMP WITH TIME ZONE NOT NULL,
    contractor_id UUID                     NOT NULL,
    vehicle_id    UUID                     NOT NULL,
    gross_weight  NUMERIC(10, 3)           NOT NULL CHECK (gross_weight >= 0),
    cell_id       UUID                     NOT NULL,
    CONSTRAINT pk_receiving_acts PRIMARY KEY (id),
    CONSTRAINT fk_receiving_act_on_contractor FOREIGN KEY (contractor_id) REFERENCES contractors (id),
    CONSTRAINT fk_receiving_act_on_cell FOREIGN KEY (cell_id) REFERENCES cells_yard (id),
    CONSTRAINT fk_receiving_act_on_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);

CREATE SEQUENCE IF NOT EXISTS shipments_doc_number_seq AS INTEGER INCREMENT BY 1;

CREATE TABLE shipments
(
    id            UUID                     NOT NULL DEFAULT gen_random_uuid(),
    doc_number    INTEGER                  NOT NULL DEFAULT nextval('shipments_doc_number_seq'),
    date_time     TIMESTAMP WITH TIME ZONE NOT NULL,
    contractor_id UUID                     NOT NULL,
    vin_code_id   UUID                     NOT NULL,
    cell_id       UUID                     NOT NULL,
    CONSTRAINT pk_shipments PRIMARY KEY (id),
    CONSTRAINT fk_shipments_on_contractor FOREIGN KEY (contractor_id) REFERENCES contractors (id),
    CONSTRAINT fk_shipments_on_cell FOREIGN KEY (cell_id) REFERENCES cells_yard (id),
    CONSTRAINT fk_shipments_on_vehicle FOREIGN KEY (vin_code_id) REFERENCES vehicles (id)
);

CREATE TABLE stock_balances
(
    id           UUID                     NOT NULL DEFAULT gen_random_uuid(),
    date_arrival TIMESTAMP WITH TIME ZONE NOT NULL,
    vehicle_id   UUID                     NOT NULL,
    cell_id      UUID                     NOT NULL,
    recorder_id  UUID                     NOT NULL,
    quantity     INTEGER                  NOT NULL,
    CONSTRAINT pk_stock_balances PRIMARY KEY (id),
    CONSTRAINT fk_stock_balances_on_cell FOREIGN KEY (cell_id) REFERENCES cells_yard (id),
    CONSTRAINT fk_stock_balances_on_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
);

CREATE TABLE vehicle_turnovers
(
    id          UUID                     NOT NULL DEFAULT gen_random_uuid(),
    period      TIMESTAMP WITH TIME ZONE NOT NULL,
    record_type INTEGER                  NOT NULL,
    vehicle_id  UUID                     NOT NULL,
    cell_id     UUID                     NOT NULL,
    recorder_id UUID                     NOT NULL,
    quantity    INTEGER                  NOT NULL,
    CONSTRAINT pk_vehicle_turnovers PRIMARY KEY (id),
    CONSTRAINT fk_vehicle_turnovers_on_cell FOREIGN KEY (cell_id) REFERENCES cells_yard (id),
    CONSTRAINT fk_vehicle_turnovers_on_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles (id)
)