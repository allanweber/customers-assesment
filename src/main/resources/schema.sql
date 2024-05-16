
CREATE TABLE IF NOT EXISTS customer (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(254) NOT NULL,
  first_name VARCHAR(254) NOT NULL,
  last_name VARCHAR(254) NOT NULL,
  date_of_birth DATE NOT NULL,
  document_number VARCHAR(20) NOT NULL,
  password VARCHAR(254) NOT NULL,
  UNIQUE (username)
);

CREATE TABLE IF NOT EXISTS customer_address (
  id INT PRIMARY KEY AUTO_INCREMENT,
  customer INT NOT NULL,
  customer_key INT NOT NULL,
  country VARCHAR(2) NOT NULL,
  postal_code VARCHAR(20) NOT NULL,
  house_number VARCHAR(20) NOT NULL,
  CONSTRAINT fk_customer_address_customer FOREIGN KEY (customer) REFERENCES customer(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS customer_account (
  id INT PRIMARY KEY AUTO_INCREMENT,
  customer INT NOT NULL,
  customer_key INT NOT NULL,
  iban VARCHAR(34) NOT NULL,
  type VARCHAR(20) NOT NULL,
  currency VARCHAR(3) NOT NULL,
  CONSTRAINT fk_customer_account_customer FOREIGN KEY (customer) REFERENCES customer(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS account_transaction (
  id INT PRIMARY KEY AUTO_INCREMENT,
  account INT NOT NULL,
  balance DECIMAL(10, 2) NOT NULL,
  CONSTRAINT fk_account_transaction_account FOREIGN KEY (account) REFERENCES customer_account(id) ON DELETE CASCADE,
  INDEX (account)
);
