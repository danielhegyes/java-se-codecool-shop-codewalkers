--Product category:
INSERT INTO product_category (ID,NAME,description,department)
VALUES (11, 'Phone' ,'A smartphone is a cellular telephone', 'Hardware');

INSERT INTO product_category (ID,NAME,description,department)
VALUES (12, 'Tablet' ,'A tablet computer', 'Hardware');

INSERT INTO product_category (ID,NAME,description,department)
VALUES (13, 'Laptop' ,'A laptop is a small computer', 'Hardware');

-- Supplier:
INSERT INTO supplier (ID,NAME,description)
VALUES (11, 'Amazon' ,'Digital content and services');

INSERT INTO supplier (ID,NAME,description)
VALUES (12, 'Lenovo' ,'Computers');

INSERT INTO supplier (ID,NAME,description)
VALUES (13, 'Apple' ,'Overpriced fancy things');


--Products:
INSERT INTO product (ID,NAME,description, default_price, default_currency, product_category, supplier)
VALUES (11, 'Amazon Fire' ,'Fantastic price. Large content ecosystem. Good parental controls. Helpful technical support.', 49.9, 'USD',
        (select id from product_category where name = 'Tablet'), (select id from supplier where name = 'Amazon'));

INSERT INTO product (ID,NAME,description, default_price, default_currency, product_category, supplier)
VALUES (12, 'Lenovo IdeaPad Miix 700' ,'Keyboard cover is included. Fanless Core m5 processor. Full-size USB ports. Adjustable kickstand.', 479, 'USD',
        (select id from product_category where name = 'Tablet'), (select id from supplier where name = 'Lenovo'));

INSERT INTO product (ID,NAME,description, default_price, default_currency, product_category, supplier)
VALUES (13, 'Amazon Fire HD 8' ,'Amazons  latest Fire HD 8 tablet is a great value for media consumption.', 89, 'USD',
        (select id from product_category where name = 'Tablet'), (select id from supplier where name = 'Amazon'));

INSERT INTO product (ID,NAME,description, default_price, default_currency, product_category, supplier)
VALUES (14, 'LENOVO IDEAPAD 100-15IBD' ,'Great Machine!!!', 350, 'USD',
        (select id from product_category where name = 'Laptop'), (select id from supplier where name = 'Lenovo'));

INSERT INTO product (ID,NAME,description, default_price, default_currency, product_category, supplier)
VALUES (15, 'Apple Iphone 9' ,'Great overpriced phone!!!', 990, 'USD',
        (select id from product_category where name = 'Phone'), (select id from supplier where name = 'Apple'));




