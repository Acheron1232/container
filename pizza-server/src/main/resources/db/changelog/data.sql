INSERT INTO pizza (id, name, description, price, images)
VALUES
    (DEFAULT, 'Margherita', 'Classic pizza with tomato sauce, mozzarella, and basil', 7.99, ARRAY[
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT6DrHsKDCiVyjqs5YHPx40BTXxRvgTWdgClQEneKBaL86l8uhc7zqp-Dsc-B6Z',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUyJnGV6HeiwA_a6IZe7pMYem3heq1IzXlQg-SKSezN0aThn7rJsgJkxLdyQ_L'
        ]),
    (DEFAULT, 'Pepperoni', 'Pepperoni, tomato sauce, mozzarella cheese', 9.49, ARRAY[
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRCLfJVMR3F9mSgjCkNQSHeGWc5JnZ3Hu-V7Ta9KVpqCH5evoH36JnZ7XkS0n7k',
        'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQtT5RaLYur--2cvhXXJLrpbK9EjjgKjAMjwi8xLIpyZG838h_r-MThXydFKDad'
        ]),
    (DEFAULT, 'Hawaiian', 'Ham, pineapple, tomato sauce, mozzarella', 10.50, ARRAY[
        'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRoyGf9-OfnyLqAuoapCat4s3cBgWLXLZvMaiPj8kd940U0pg8nmHR3MzNQV1Hc',
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQovwI9GlxCc20YKn2iIKQxvDPwg-tosZST_7_8wApKEMRhFBqQcfcR4QPikEE1'
        ]),
    (DEFAULT, 'Veggie Delight', 'Bell peppers, onions, mushrooms, olives, tomato sauce, mozzarella', 8.75, ARRAY[
        'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSKWfuJ3Vcwke95VgpgMSZ5WrdGMMLEaXNJFQbzYXsNgOX4uNy3oTnAc-v0P7Ip',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTR-QK0qrYPwSJAHQSyBdU3g8EBvTPJ5of9hy4EoJAjTwTQcn4YXO28neXJ7mVs'
        ]),
    (DEFAULT, 'BBQ Chicken', 'Chicken, BBQ sauce, red onions, mozzarella', 11.25, ARRAY[
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT6DrHsKDCiVyjqs5YHPx40BTXxRvgTWdgClQEneKBaL86l8uhc7zqp-Dsc-B6Z',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUyJnGV6HeiwA_a6IZe7pMYem3heq1IzXlQg-SKSezN0aThn7rJsgJkxLdyQ_L'
        ]),
    (DEFAULT, 'Four Cheese', 'Mozzarella, cheddar, parmesan, gorgonzola, tomato sauce', 12.00, ARRAY[
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRCLfJVMR3F9mSgjCkNQSHeGWc5JnZ3Hu-V7Ta9KVpqCH5evoH36JnZ7XkS0n7k'
        ]),
    (DEFAULT, 'Meat Lovers', 'Pepperoni, sausage, ham, bacon, tomato sauce, mozzarella', 13.50, ARRAY[
        'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcQtT5RaLYur--2cvhXXJLrpbK9EjjgKjAMjwi8xLIpyZG838h_r-MThXydFKDad',
        'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRoyGf9-OfnyLqAuoapCat4s3cBgWLXLZvMaiPj8kd940U0pg8nmHR3MzNQV1Hc'
        ]),
    (DEFAULT, 'Mediterranean', 'Olives, feta, spinach, tomato sauce, mozzarella', 10.00, ARRAY[
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQovwI9GlxCc20YKn2iIKQxvDPwg-tosZST_7_8wApKEMRhFBqQcfcR4QPikEE1',
        'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSKWfuJ3Vcwke95VgpgMSZ5WrdGMMLEaXNJFQbzYXsNgOX4uNy3oTnAc-v0P7Ip'
        ]),
    (DEFAULT, 'Buffalo Chicken', 'Buffalo chicken, ranch sauce, mozzarella, red onions', 11.75, ARRAY[
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTR-QK0qrYPwSJAHQSyBdU3g8EBvTPJ5of9hy4EoJAjTwTQcn4YXO28neXJ7mVs',
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTxehmE1jNMofgJ3zgtrMP7I6IDwLlXwUNJtZ5p1p-EN1nJ8ISrsO6QmT5a15wb'
        ]),
    (DEFAULT, 'Mushroom Swiss', 'Mushrooms, Swiss cheese, garlic sauce', 9.99, ARRAY[
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcT6DrHsKDCiVyjqs5YHPx40BTXxRvgTWdgClQEneKBaL86l8uhc7zqp-Dsc-B6Z'
        ]),
    (DEFAULT, 'Spinach Alfredo', 'Spinach, alfredo sauce, mozzarella, parmesan', 10.50, ARRAY[
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUyJnGV6HeiwA_a6IZe7pMYem3heq1IzXlQg-SKSezN0aThn7rJsgJkxLdyQ_L',
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRCLfJVMR3F9mSgjCkNQSHeGWc5JnZ3Hu-V7Ta9KVpqCH5evoH36JnZ7XkS0n7k'
        ]),
    (DEFAULT, 'Taco Pizza', 'Ground beef, cheddar, lettuce, tomato, salsa', 12.25, ARRAY[
        'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSKWfuJ3Vcwke95VgpgMSZ5WrdGMMLEaXNJFQbzYXsNgOX4uNy3oTnAc-v0P7Ip',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTR-QK0qrYPwSJAHQSyBdU3g8EBvTPJ5of9hy4EoJAjTwTQcn4YXO28neXJ7mVs'
        ]),
    (DEFAULT, 'Prosciutto Arugula', 'Prosciutto, arugula, mozzarella, tomato sauce', 13.00, ARRAY[
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQovwI9GlxCc20YKn2iIKQxvDPwg-tosZST_7_8wApKEMRhFBqQcfcR4QPikEE1',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxehmE1jNMofgJ3zgtrMP7I6IDwLlXwUNJtZ5p1p-EN1nJ8ISrsO6QmT5a15wb'
        ]),
    (DEFAULT, 'Pesto Chicken', 'Pesto sauce, chicken, mozzarella, cherry tomatoes', 11.50, ARRAY[
        'https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRoyGf9-OfnyLqAuoapCat4s3cBgWLXLZvMaiPj8kd940U0pg8nmHR3MzNQV1Hc',
        'https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcQovwI9GlxCc20YKn2iIKQxvDPwg-tosZST_7_8wApKEMRhFBqQcfcR4QPikEE1'
        ]),
    (DEFAULT, 'Greek', 'Feta, olives, tomato, red onion, mozzarella', 10.25, ARRAY[
        'https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcSKWfuJ3Vcwke95VgpgMSZ5WrdGMMLEaXNJFQbzYXsNgOX4uNy3oTnAc-v0P7Ip',
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTR-QK0qrYPwSJAHQSyBdU3g8EBvTPJ5of9hy4EoJAjTwTQcn4YXO28neXJ7mVs'
        ])