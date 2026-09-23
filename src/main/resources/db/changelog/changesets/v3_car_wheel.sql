--liquibase formatted sql

--changeset matvey:add-wheel-to-cars
ALTER TABLE cars ADD COLUMN wheel_id UUID REFERENCES details(id);
