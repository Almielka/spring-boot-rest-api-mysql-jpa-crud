INSERT INTO company VALUES (1, 'company1-1' , 'TYPE1');
INSERT INTO company VALUES (2, 'company2-2', 'TYPE2');
INSERT INTO company VALUES  (3, 'company3-2', 'TYPE2');
INSERT INTO company VALUES  (4, 'company4-3', 'TYPE3');
INSERT INTO company VALUES  (5, 'company5-1', 'TYPE1');

INSERT INTO product VALUES (1,  'product1', '2017-12-10 06:50:07.996', 100, 1);
INSERT INTO product VALUES (2, 'product2', '2018-01-22 06:50:07.996', 1000, 5);
INSERT INTO product VALUES (3, 'product3',  '2019-03-05 06:50:07.996', 100.5, 4);
INSERT INTO product VALUES (4, 'product4', '2019-08-26 06:50:07.996', 10,  2);
INSERT INTO product VALUES (5, 'product5', '2019-12-15 06:50:07.996', 20.5, 3);
INSERT INTO product VALUES (6, 'product6', '2020-01-12 06:50:07.996', 10.2, 2);

INSERT INTO order_product VALUES (1, '2020-03-24T19:46:07.99');
INSERT INTO order_product VALUES (2, '2020-03-24T19:47:07.99');
INSERT INTO order_product VALUES (3, '2020-03-24T19:48:07.99');

INSERT INTO product_order_product VALUES (3, 1);
INSERT INTO product_order_product VALUES (4, 1);
INSERT INTO product_order_product VALUES (1, 2);
INSERT INTO product_order_product VALUES (2, 2);
INSERT INTO product_order_product VALUES (3, 3);
INSERT INTO product_order_product VALUES (4, 3);

