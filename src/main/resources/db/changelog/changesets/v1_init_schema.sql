--liquibase formatted sql

--changeset matvey:1
CREATE TABLE app_users (
                           id UUID PRIMARY KEY,
                           username VARCHAR(255) UNIQUE NOT NULL,
                           password VARCHAR(255),
                           full_name VARCHAR(255),
                           role VARCHAR(50),
                           created_at TIMESTAMP,
                           updated_at TIMESTAMP,
                           removed BOOLEAN DEFAULT FALSE
);

--changeset matvey:2
CREATE TABLE details (
                         id UUID PRIMARY KEY,
                         type VARCHAR(100) NOT NULL,
                         value VARCHAR(255) NOT NULL,
                         cost DOUBLE PRECISION NOT NULL,
                         created_at TIMESTAMP,
                         updated_at TIMESTAMP,
                         removed BOOLEAN DEFAULT FALSE
);

--changeset matvey:3
CREATE TABLE detail_compatibility (
    detail_id UUID NOT NULL REFERENCES details(id) ON DELETE CASCADE,
    model_name VARCHAR(255) NOT NULL
);

--changeset matvey:4
CREATE TABLE cars (
                      id UUID PRIMARY KEY,
                      brand VARCHAR(255) NOT NULL,
                      model VARCHAR(255) NOT NULL,
                      base_cost DOUBLE PRECISION NOT NULL,
                      body_id UUID REFERENCES details(id),
                      fuel_id UUID REFERENCES details(id),
                      transmission_id UUID REFERENCES details(id),
                      color_id UUID REFERENCES details(id),
                      drive_layout_id UUID REFERENCES details(id),
                      created_at TIMESTAMP,
                      updated_at TIMESTAMP,
                      removed BOOLEAN DEFAULT FALSE
);

--changeset matvey:5
CREATE TABLE car_additional_details (
    car_id UUID NOT NULL REFERENCES cars(id) ON DELETE CASCADE,
    detail_id UUID NOT NULL REFERENCES details(id) ON DELETE CASCADE,
    PRIMARY KEY (car_id, detail_id)
);

--changeset matvey:6
CREATE TABLE complication_orders (
    id UUID PRIMARY KEY,
    client_id UUID REFERENCES app_users(id),
    manager_id UUID REFERENCES app_users(id),
    car_id UUID REFERENCES cars(id),
    status VARCHAR(100),
    final_price DOUBLE PRECISION,
    configuration_snapshot JSONB,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    removed BOOLEAN DEFAULT FALSE
);

--changeset matvey:7
CREATE TABLE stock_car_orders (
    id UUID PRIMARY KEY,
    client_id UUID REFERENCES app_users(id),
    manager_id UUID REFERENCES app_users(id),
    car_id UUID UNIQUE REFERENCES cars(id),
    status VARCHAR(100),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    removed BOOLEAN DEFAULT FALSE
);

--changeset matvey:8
CREATE TABLE test_drive_orders (
    id UUID PRIMARY KEY,
    client_id UUID REFERENCES app_users(id),
    manager_id UUID REFERENCES app_users(id),
    car_id UUID REFERENCES cars(id),
    slot_time TIMESTAMP,
    status VARCHAR(100),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    removed BOOLEAN DEFAULT FALSE
);