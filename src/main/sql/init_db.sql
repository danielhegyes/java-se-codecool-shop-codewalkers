DROP TABLE if EXISTS product, product_category,supplier;

CREATE TABLE product_category
(
  id INTEGER PRIMARY KEY,
  name varchar(40),
  description varchar(40),
  department VARCHAR(40)
);

CREATE TABLE supplier
(
  id INTEGER PRIMARY KEY,
  name varchar(40),
  description varchar(40)
);

CREATE TABLE product
(
  id INTEGER PRIMARY KEY,
  name varchar(40),
  description varchar(40),
  default_price DOUBLE PRECISION,
  default_currency varchar(3),
  product_category INTEGER REFERENCES product_category(id),
  supplier INTEGER REFERENCES supplier(id)
);





