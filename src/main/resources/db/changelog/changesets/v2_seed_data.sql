--liquibase formatted sql

--changeset matvey:seed-users
INSERT INTO app_users (id, username, password, full_name, role, created_at, updated_at, removed) VALUES
('11111111-1111-1111-1111-111111111111', 'admin', 'admin123', 'Главный Администратор', 'ADMIN', NOW(), NOW(), false),
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'client2', 'client123', 'Обычный Клиент2', 'CLIENT', NOW(), NOW(), false),
('22222222-2222-2222-2222-222222222222', 'client', 'client123', 'Обычный Клиент', 'CLIENT', NOW(), NOW(), false);

--changeset matvey:seed-details
INSERT INTO details (id, type, value, cost, created_at, updated_at, removed) VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'BODY', 'Sedan', 50000.00, NOW(), NOW(), false),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'FUEL', 'Petrol', 30000.00, NOW(), NOW(), false),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'TRANSMISSION', 'Automatic', 40000.00, NOW(), NOW(), false),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4', 'COLOR', 'Black', 15000.00, NOW(), NOW(), false),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5', 'DRIVELAYOUT', 'AWD', 60000.00, NOW(), NOW(), false),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa6', 'WHEEL', 'Round24', 5000.00, NOW(), NOW(), false);

--changeset matvey:seed-cars
INSERT INTO cars (id, brand, model, base_cost, body_id, fuel_id, transmission_id, color_id, drive_layout_id, created_at, updated_at, removed) VALUES
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'BMW', 'M5', 100000.00,
'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3',
'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4',
'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5',
NOW(), NOW(), false);

--changeset matvey:seed-car-additional-details
INSERT INTO car_additional_details (car_id, detail_id) VALUES
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa6');
