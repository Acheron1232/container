--liquibase formatted sql

--changeset artem:1
CREATE TABLE orders
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id     BIGINT         NOT NULL,
    facility_id UUID           NOT NULL,
    table_id    UUID           NOT NULL,
    status      VARCHAR(50)    NOT NULL,
    total_price NUMERIC(15, 2) NOT NULL,
    type        VARCHAR(50)    NOT NULL,
    created_at  TIMESTAMP        DEFAULT CURRENT_TIMESTAMP,
    placed_at   TIMESTAMP
);

--changeset artem:2
CREATE TABLE order_items
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES orders (id),
    pizza_id UUID NOT NULL,
    quantity INT  NOT NULL CHECK (quantity > 0)
);

--changeset artem:3
CREATE TABLE payment
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID        NOT NULL REFERENCES orders (id),
    method   VARCHAR(50) NOT NULL,
    status   VARCHAR(50) NOT NULL
);