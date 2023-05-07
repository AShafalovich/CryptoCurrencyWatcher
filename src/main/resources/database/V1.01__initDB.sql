CREATE TABLE IF NOT EXISTS users
(
    id     SERIAL PRIMARY KEY,
    name   VARCHAR(25)      NOT NULL,
    symbol VARCHAR(10)      NOT NULL,
    price  DOUBLE PRECISION NOT NULL
);

CREATE TABLE IF NOT EXISTS cryptocurrencies
(
    id     INTEGER          NOT NULL,
    symbol VARCHAR(10)      NOT NULL,
    price  DOUBLE PRECISION NOT NULL
);