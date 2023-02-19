CREATE TABLE products (
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL,
  description TEXT,
  unit_value NUMERIC(10, 2) NOT NULL,
  creation_date TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE orders (
  id SERIAL PRIMARY KEY,
  product_id JSONB NOT NULL,
  total_value NUMERIC(10, 2) NOT NULL,
  creation_date TIMESTAMP NOT NULL DEFAULT NOW(),
  status TEXT NOT NULL
);

CREATE TABLE tms_order (
  id SERIAL PRIMARY KEY,
  order_id JSONB NOT NULL,
  status TEXT NOT NULL
);