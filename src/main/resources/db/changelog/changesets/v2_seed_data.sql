--liquibase formatted sql

--changeset matvey:9
INSERT INTO app_users (id, username, password, full_name, role, created_at, updated_at, removed) VALUES
('11111111-1111-1111-1111-111111111111', 'admin', 'admin123', 'Главный Администратор', 'ADMIN', NOW(), NOW(), false),
('22222222-2222-2222-2222-222222222222', 'client', 'client123', 'Обычный Клиент', 'CLIENT', NOW(), NOW(), false);

--changeset matvey:10
INSERT INTO details (id, type, detail_info, cost, created_at, updated_at, removed) VALUES
                                                                                       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'BODY', 'Sedan', 50000.00, NOW(), NOW(), false),
                                                                                       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'FUEL', 'Petrol', 30000.00, NOW(), NOW(), false),
                                                                                       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'TRANSMISSION', 'Automatic', 40000.00, NOW(), NOW(), false),
                                                                                       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4', 'COLOR', 'Black', 15000.00, NOW(), NOW(), false),
                                                                                       ('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5', 'DRIVELAYOUT', 'AWD', 60000.00, NOW(), NOW(), false);

--changeset matvey:11
INSERT INTO detail_compatibility (detail_id, model_name) VALUES
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1', 'M5'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2', 'M5'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3', 'M5'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4', 'M5'),
('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5', 'M5');

--changeset matvey:12
INSERT INTO cars (id, brand, model, base_cost, body_id, fuel_id, transmission_id, color_id, drive_layout_id, created_at, updated_at, removed) VALUES
('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', 'BMW', 'M5', 100000.00,
'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa1',
'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa2',
'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa3',
'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa4',
'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaa5',
NOW(), NOW(), false);

