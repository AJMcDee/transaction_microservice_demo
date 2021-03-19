CREATE TABLE IF NOT EXISTS transactions
(
    id bigserial PRIMARY KEY NOT NULL,
    from_iban varchar(100),
    to_iban varchar(100),
    date varchar(100),
    type varchar(100),
    amount DECIMAL
);