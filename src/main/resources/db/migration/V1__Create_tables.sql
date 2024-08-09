-- noinspection SqlNoDataSourceInspectionForFile

CREATE TABLE buy (
    id SERIAL PRIMARY KEY NOT NULL,
    item_id INTEGER NOT NULL,
    amount_bought INTEGER NOT NULL,
    price_per_item FLOAT NOT NULL,
    date TIMESTAMP DEFAULT (now() AT TIME ZONE 'UTC')
);

CREATE TABLE sell (
    id SERIAL PRIMARY KEY NOT NULL,
    item_id INTEGER NOT NULL,
    amount_sold INTEGER NOT NULL,
    price_per_item FLOAT NOT NULL,
    date TIMESTAMP DEFAULT (now() AT TIME ZONE 'UTC')
);

CREATE TABLE items (
    id SERIAL PRIMARY KEY NOT NULL,
    current_stock INTEGER NOT NULL,
    item_name TEXT NOT NULL,
    important BOOLEAN NOT NULL
);

CREATE TABLE expense (
    id SERIAL PRIMARY KEY NOT NULL,
    amount FLOAT NOT NULL,
    reason TEXT NOT NULL,
    date TIMESTAMP DEFAULT (now() AT TIME ZONE 'UTC')
);

CREATE TABLE profit (
    id SERIAL PRIMARY KEY NOT NULL,
    amount FLOAT NOT NULL,
    reason TEXT NOT NULL,
    date TIMESTAMP DEFAULT (now() AT TIME ZONE 'UTC')
);