--liquibase formatted sql

--changeset artem:1
CREATE TABLE orders
(
    id         UUID PRIMARY KEY   DEFAULT gen_random_uuid(),
    user_id    BIGINT    NOT NULL,
    order_time TIMESTAMP NOT NULL DEFAULT NOW()
);
--changeset artem:2
CREATE TABLE pizza
(
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    images      text[],
    price       int          NOT NULL
);
--changeset artem:3
CREATE TABLE delivery
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id      UUID references orders (id),
    address       VARCHAR(255) NOT NULL,
    delivery_time TIMESTAMP    NOT NULL,
    status        VARCHAR(50)  NOT NULL,
    delivery_man  BIGINT       NOT NULL,
    receiver      BIGINT       NOT NULL
);

--changeset artem:4
CREATE TABLE order_pizza
(
    id       UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    order_id UUID NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    pizza_id UUID NOT NULL REFERENCES pizza (id) ON DELETE CASCADE,
    quantity INT  NOT NULL CHECK (quantity > 0)
);