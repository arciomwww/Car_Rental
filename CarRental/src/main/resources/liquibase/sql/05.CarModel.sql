CREATE TABLE CarModel (
    id SERIAL PRIMARY KEY,
    brand VARCHAR(64) NOT NULL,
    model VARCHAR(64) NOT NULL,
    year INT NOT NULL
);