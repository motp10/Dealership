--liquibase formatted sql

--changeset matvey:1
CREATE TABLE complication_orders (
    id UUID PRIMARY KEY,
    client_id UUID,
    manager_id UUID,
    car_id UUID,
    status VARCHAR(100),
    final_price DOUBLE PRECISION,
    configuration_snapshot JSONB,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    removed BOOLEAN DEFAULT FALSE
);

--changeset matvey:2
CREATE TABLE stock_car_orders (
    id UUID PRIMARY KEY,
    client_id UUID,
    manager_id UUID,
    car_id UUID UNIQUE,
    status VARCHAR(100),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    removed BOOLEAN DEFAULT FALSE
);

--changeset matvey:3
CREATE TABLE test_drive_orders (
    id UUID PRIMARY KEY,
    client_id UUID,
    manager_id UUID,
    car_id UUID,
    slot_time TIMESTAMP,
    status VARCHAR(100),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    removed BOOLEAN DEFAULT FALSE
);

--changeset matvey:4
CREATE TABLE outbox_messages (
    id UUID PRIMARY KEY,
    payload JSONB,
    status VARCHAR(50),
    event_type VARCHAR(255),
    created_at TIMESTAMP
);
