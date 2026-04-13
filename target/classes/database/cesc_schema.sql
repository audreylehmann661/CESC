CREATE DATABASE IF NOT EXISTS cesc_db;
USE cesc_db;

DROP TABLE IF EXISTS payments;
DROP TABLE IF EXISTS wallet_transactions;
DROP TABLE IF EXISTS trips;
DROP TABLE IF EXISTS scooters;
DROP TABLE IF EXISTS stations;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('USER', 'SPONSOR', 'MAINTAINER', 'ADMIN') NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE stations (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    location VARCHAR(255) NOT NULL,
    capacity INT NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE', 'MAINTENANCE') NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE scooters (
    id INT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(30) NOT NULL UNIQUE,
    make VARCHAR(80) NOT NULL,
    model VARCHAR(80) NOT NULL,
    color VARCHAR(40) NOT NULL,
    battery_capacity INT NOT NULL,
    battery_level INT NOT NULL,
    status ENUM('AVAILABLE', 'IN_USE', 'CHARGING', 'MAINTENANCE') NOT NULL DEFAULT 'AVAILABLE',
    sponsor_user_id INT NULL,
    station_id INT NULL,
    last_service_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_scooter_sponsor
        FOREIGN KEY (sponsor_user_id) REFERENCES users(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    CONSTRAINT fk_scooter_station
        FOREIGN KEY (station_id) REFERENCES stations(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE trips (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    scooter_id INT NOT NULL,
    start_station_id INT NOT NULL,
    end_station_id INT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NULL,
    distance_km DECIMAL(6,2) NOT NULL DEFAULT 0.00,
    cost DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    status ENUM('COMPLETED', 'ACTIVE', 'CANCELLED') NOT NULL DEFAULT 'COMPLETED',
    CONSTRAINT fk_trip_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_trip_scooter
        FOREIGN KEY (scooter_id) REFERENCES scooters(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_trip_start_station
        FOREIGN KEY (start_station_id) REFERENCES stations(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_trip_end_station
        FOREIGN KEY (end_station_id) REFERENCES stations(id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

CREATE TABLE wallet_transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    transaction_type ENUM('CREDIT', 'DEBIT') NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    description VARCHAR(255) NOT NULL,
    transaction_date DATETIME NOT NULL,
    CONSTRAINT fk_wallet_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE payments (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    trip_id INT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_method ENUM('CARD', 'WALLET', 'TRANSFER') NOT NULL,
    payment_status ENUM('PAID', 'PENDING', 'FAILED') NOT NULL DEFAULT 'PAID',
    payment_date DATETIME NOT NULL,
    CONSTRAINT fk_payment_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT fk_payment_trip
        FOREIGN KEY (trip_id) REFERENCES trips(id)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

INSERT INTO users (name, email, password, role) VALUES
('Ava Rider', 'ava@cesc.ca', 'pass123', 'USER'),
('Liam Commuter', 'liam@cesc.ca', 'pass123', 'USER'),
('Noah Sponsor', 'noah@cesc.ca', 'pass123', 'SPONSOR'),
('Mia Maintainer', 'mia@cesc.ca', 'pass123', 'MAINTAINER'),
('Admin Lead', 'admin@cesc.ca', 'admin123', 'ADMIN');

INSERT INTO stations (name, location, capacity, status) VALUES
('ACCE Building Station', 'ACCE Building - Woodroffe Campus', 20, 'ACTIVE'),
('Student Commons Station', 'Student Commons - Woodroffe Campus', 18, 'ACTIVE'),
('Library Station', 'Library / C Building - Woodroffe Campus', 12, 'ACTIVE'),
('Residence South Station', 'South Residence - Woodroffe Campus', 10, 'MAINTENANCE');

INSERT INTO scooters (code, make, model, color, battery_capacity, battery_level, status, sponsor_user_id, station_id, last_service_date) VALUES
('SC-1001', 'Segway', 'Ninebot Max', 'Black', 551, 92, 'AVAILABLE', 3, 1, '2026-03-15'),
('SC-1002', 'Segway', 'Ninebot Max', 'Blue', 551, 81, 'AVAILABLE', 3, 1, '2026-03-16'),
('SC-1003', 'NIU', 'KQi3 Pro', 'White', 486, 47, 'CHARGING', 3, 1, '2026-03-10'),
('SC-1004', 'NIU', 'KQi3 Pro', 'Gray', 486, 74, 'AVAILABLE', 3, 2, '2026-03-12'),
('SC-1005', 'Apollo', 'City', 'Red', 500, 68, 'IN_USE', 3, 2, '2026-03-11'),
('SC-1006', 'Apollo', 'City', 'Black', 500, 88, 'AVAILABLE', 3, 2, '2026-03-14'),
('SC-1007', 'Segway', 'Max G2', 'Yellow', 551, 51, 'MAINTENANCE', 3, 3, '2026-02-28'),
('SC-1008', 'Segway', 'Max G2', 'Black', 551, 97, 'AVAILABLE', 3, 3, '2026-03-18'),
('SC-1009', 'NIU', 'KQi2 Pro', 'Silver', 365, 64, 'AVAILABLE', 3, 3, '2026-03-17'),
('SC-1010', 'NIU', 'KQi2 Pro', 'White', 365, 39, 'CHARGING', 3, 4, '2026-03-08');

INSERT INTO trips (user_id, scooter_id, start_station_id, end_station_id, start_time, end_time, distance_km, cost, status) VALUES
(1, 1, 1, 2, '2026-01-08 08:15:00', '2026-01-08 08:42:00', 6.40, 8.50, 'COMPLETED'),
(2, 4, 2, 1, '2026-01-16 17:05:00', '2026-01-16 17:29:00', 5.10, 7.25, 'COMPLETED'),
(1, 2, 1, 3, '2026-02-02 09:10:00', '2026-02-02 09:48:00', 7.80, 9.90, 'COMPLETED'),
(2, 6, 2, 3, '2026-02-11 18:02:00', '2026-02-11 18:40:00', 6.70, 8.75, 'COMPLETED'),
(1, 8, 3, 1, '2026-03-05 07:52:00', '2026-03-05 08:21:00', 5.90, 7.80, 'COMPLETED'),
(2, 9, 3, 2, '2026-03-12 16:33:00', '2026-03-12 17:06:00', 6.20, 8.10, 'COMPLETED');

INSERT INTO wallet_transactions (user_id, transaction_type, amount, description, transaction_date) VALUES
(1, 'CREDIT', 40.00, 'Wallet top-up', '2026-01-03 10:00:00'),
(1, 'DEBIT', 8.50, 'Trip fare deduction', '2026-01-08 08:42:00'),
(2, 'CREDIT', 30.00, 'Wallet top-up', '2026-01-10 12:15:00'),
(2, 'DEBIT', 7.25, 'Trip fare deduction', '2026-01-16 17:29:00'),
(1, 'CREDIT', 25.00, 'Sponsor credit', '2026-02-01 09:00:00'),
(1, 'DEBIT', 9.90, 'Trip fare deduction', '2026-02-02 09:48:00'),
(2, 'DEBIT', 8.75, 'Trip fare deduction', '2026-02-11 18:40:00'),
(1, 'DEBIT', 7.80, 'Trip fare deduction', '2026-03-05 08:21:00'),
(2, 'CREDIT', 20.00, 'Wallet top-up', '2026-03-09 14:30:00'),
(2, 'DEBIT', 8.10, 'Trip fare deduction', '2026-03-12 17:06:00');

INSERT INTO payments (user_id, trip_id, amount, payment_method, payment_status, payment_date) VALUES
(1, 1, 8.50, 'WALLET', 'PAID', '2026-01-08 08:42:00'),
(2, 2, 7.25, 'CARD', 'PAID', '2026-01-16 17:29:00'),
(1, 3, 9.90, 'WALLET', 'PAID', '2026-02-02 09:48:00'),
(2, 4, 8.75, 'CARD', 'PAID', '2026-02-11 18:40:00'),
(1, 5, 7.80, 'WALLET', 'PAID', '2026-03-05 08:21:00'),
(2, 6, 8.10, 'TRANSFER', 'PAID', '2026-03-12 17:06:00');

-- ERD summary
-- users 1---* trips
-- users 1---* wallet_transactions
-- users 1---* payments
-- stations 1---* scooters
-- stations 1---* trips (start and end station)
-- scooters 1---* trips
-- trips 1---0..1 payments
