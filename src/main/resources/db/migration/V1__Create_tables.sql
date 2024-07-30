CREATE TABLE buy (
    id SERIAL PRIMARY KEY NOT NULL,
    item_id INTEGER NOT NULL,
    amount_bought INTEGER NOT NULL,
    price_per_item INTEGER NOT NULL
);

CREATE TABLE sell (
    id SERIAL PRIMARY KEY NOT NULL,
    item_id INTEGER NOT NULL,
    amount_sold INTEGER NOT NULL,
    price_per_item INTEGER NOT NULL
);

CREATE TABLE items (
    id SERIAL PRIMARY KEY NOT NULL,
    current_stock INTEGER NOT NULL,
    item_name TEXT NOT NULL,
    important BOOLEAN NOT NULL
);