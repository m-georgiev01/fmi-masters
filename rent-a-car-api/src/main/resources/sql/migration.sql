CREATE TABLE td_cities (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NUL
);

INSERT INTO td_cities(name)
VALUES
    ('Plovdiv'),
    ('Sofia'),
    ('Varna'),
    ('Burgas')

CREATE TABLE td_cars (
    id INT AUTO_INCREMENT PRIMARY KEY,
    make VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    is_available BOOLEAN DEFAULT TRUE,
    daily_price DECIMAL(10,2) NOT NULL,
    city_id INT,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (city_id) REFERENCES td_cities(id)
);

INSERT INTO td_cars(make, model, daily_price, city_id)
VALUES
    ('Mercedes', 'C-class', 100, 1),
    ('Volkswagen', 'Golf', 55, 2),
    ('Volkswagen', 'Passat', 80, 3),
    ('Mercedes', 'E-class', 125, 4)

CREATE TABLE td_clients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    has_accidents BOOLEAN NOT NULL
);

INSERT INTO td_clients (name, has_accidents)
VALUES
    ('Pesho', TRUE),
    ('Gosho', FALSE)

CREATE TABLE td_offers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    client_id INT,
    car_id INT,
    status_id INT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    is_accepted BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (client_id) REFERENCES td_clients(id),
    FOREIGN KEY (car_id) REFERENCES td_cars(id)
);