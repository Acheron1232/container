--liquibase formatted sql

--changeset artem:5
-- pizzas
INSERT INTO pizza (id, name, description, price, images)
VALUES (DEFAULT, 'Margherita', 'Classic pizza with tomato sauce, mozzarella, and basil', 7.99, ARRAY [
    'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT6DrHsKDCiVyjqs5YHPx40BTXxRvgTWdgClQEneKBaL86l8uhc7zqp-Dsc-B6Z',
    'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUyJnGV6HeiwA_a6IZe7pMYem3heq1IzXlQg-SKSezN0aThn7rJsgJkxLdyQ_L'
    ]),
       (DEFAULT, 'Pepperoni', 'Pepperoni, tomato sauce, mozzarella cheese', 9.49, ARRAY [
           'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRCLfJVMR3F9mSgjCkNQSHeGWc5JnZ3Hu-V7Ta9KVpqCH5evoH36JnZ7XkS0n7k',
           'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQtT5RaLYur--2cvhXXJLrpbK9EjjgKjAMjwi8xLIpyZG838h_r-MThXydFKDad'
           ]),
       (DEFAULT, 'Hawaiian', 'Ham, pineapple, tomato sauce, mozzarella', 10.50, ARRAY [
           'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRoyGf9-OfnyLqAuoapCat4s3cBgWLXLZvMaiPj8kd940U0pg8nmHR3MzNQV1Hc',
           'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQovwI9GlxCc20YKn2iIKQxvDPwg-tosZST_7_8wApKEMRhFBqQcfcR4QPikEE1'
           ]),
       (DEFAULT, 'Veggie Delight', 'Bell peppers, onions, mushrooms, olives, tomato sauce, mozzarella', 8.75, ARRAY [
           'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSKWfuJ3Vcwke95VgpgMSZ5WrdGMMLEaXNJFQbzYXsNgOX4uNy3oTnAc-v0P7Ip',
           'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTR-QK0qrYPwSJAHQSyBdU3g8EBvTPJ5of9hy4EoJAjTwTQcn4YXO28neXJ7mVs'
           ]);

--changeset artem:6
-- ingredients
INSERT INTO ingredient (id, name)
VALUES (DEFAULT, 'Tomato Sauce'),
       (DEFAULT, 'Mozzarella'),
       (DEFAULT, 'Basil'),
       (DEFAULT, 'Pepperoni'),
       (DEFAULT, 'Ham'),
       (DEFAULT, 'Pineapple'),
       (DEFAULT, 'Bell Peppers'),
       (DEFAULT, 'Onions'),
       (DEFAULT, 'Mushrooms'),
       (DEFAULT, 'Olives');

--changeset artem:7
-- pizza_ingredient relations
-- Margherita
INSERT INTO pizza_ingredient (pizza_id, ingredient_id)
SELECT p.id, i.id
FROM pizza p,
     ingredient i
WHERE p.name = 'Margherita'
  AND i.name IN ('Tomato Sauce', 'Mozzarella', 'Basil');

-- Pepperoni
INSERT INTO pizza_ingredient (pizza_id, ingredient_id)
SELECT p.id, i.id
FROM pizza p,
     ingredient i
WHERE p.name = 'Pepperoni'
  AND i.name IN ('Tomato Sauce', 'Mozzarella', 'Pepperoni');

-- Hawaiian
INSERT INTO pizza_ingredient (pizza_id, ingredient_id)
SELECT p.id, i.id
FROM pizza p,
     ingredient i
WHERE p.name = 'Hawaiian'
  AND i.name IN ('Tomato Sauce', 'Mozzarella', 'Ham', 'Pineapple');

-- Veggie Delight
INSERT INTO pizza_ingredient (pizza_id, ingredient_id)
SELECT p.id, i.id
FROM pizza p,
     ingredient i
WHERE p.name = 'Veggie Delight'
  AND i.name IN ('Tomato Sauce', 'Mozzarella', 'Bell Peppers', 'Onions', 'Mushrooms', 'Olives');

--changeset artem:8
-- facilities
INSERT INTO facility (id, name, address, city, capacity)
VALUES (DEFAULT, 'Central Pizza House', '123 Main St', 'Kyiv', 50),
       (DEFAULT, 'Lviv Pizzeria', '45 Shevchenko Ave', 'Lviv', 40),
       (DEFAULT, 'Odessa Pizza Hub', '12 Deribasivska St', 'Odessa', 60),
       (DEFAULT, 'Kharkiv Pizza Spot', '99 Freedom Sq', 'Kharkiv', 35);
