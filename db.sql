-- USERS TABLE
CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(100) NOT NULL,
                       last_name VARCHAR(100) NOT NULL,
                        phone VARCHAR(11) NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- WALLETS TABLE
CREATE TABLE wallets (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         user_id INT NOT NULL UNIQUE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- CURRENCIES TABLE
CREATE TABLE currencies (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            code VARCHAR(10) NOT NULL UNIQUE, -- BTC, ETH, USD, etc.
                            name VARCHAR(50) NOT NULL
);

-- WALLET BALANCES TABLE
CREATE TABLE wallet_balances (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 wallet_id INT NOT NULL,
                                 currency_id INT NOT NULL,
                                 amount DECIMAL(20, 8) NOT NULL DEFAULT 0.0,
                                 UNIQUE(wallet_id, currency_id),
                                 FOREIGN KEY (wallet_id) REFERENCES wallets(id) ON DELETE CASCADE,
                                 FOREIGN KEY (currency_id) REFERENCES currencies(id)
);

-- ORDER TYPES TABLE (e.g., BUY, SELL)
CREATE TABLE order_types (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             type VARCHAR(10) NOT NULL UNIQUE -- BUY, SELL
);

-- ORDER STATUSES TABLE (e.g., OPEN, CLOSED)
CREATE TABLE order_statuses (
                                id INT AUTO_INCREMENT PRIMARY KEY,
                                status VARCHAR(20) NOT NULL UNIQUE -- OPEN, CLOSED
);

-- ORDERS TABLE
CREATE TABLE orders (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        user_id INT NOT NULL,
                        base_currency_id INT NOT NULL,     -- currency being traded
                        quote_currency_id INT NOT NULL,    -- currency used to price the trade
                        order_type_id INT NOT NULL,        -- FK to order_types
                        price DECIMAL(20, 8) NOT NULL,     -- price per unit of base currency
                        amount DECIMAL(20, 8) NOT NULL,    -- total amount of base currency
                        filled DECIMAL(20, 8) NOT NULL DEFAULT 0.0,
                        order_status_id INT NOT NULL,      -- FK to order_statuses
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES users(id),
                        FOREIGN KEY (base_currency_id) REFERENCES currencies(id),
                        FOREIGN KEY (quote_currency_id) REFERENCES currencies(id),
                        FOREIGN KEY (order_type_id) REFERENCES order_types(id),
                        FOREIGN KEY (order_status_id) REFERENCES order_statuses(id)
);

-- TRADES TABLE
CREATE TABLE trades (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        buy_order_id INT NOT NULL,
                        sell_order_id INT NOT NULL,
                        price DECIMAL(20, 8) NOT NULL,
                        amount DECIMAL(20, 8) NOT NULL,
                        timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (buy_order_id) REFERENCES orders(id),
                        FOREIGN KEY (sell_order_id) REFERENCES orders(id)
);


-- Order types
INSERT INTO order_types (type) VALUES ('BUY'), ('SELL');

-- Order statuses
INSERT INTO order_statuses (status) VALUES ('OPEN_FILLED'), ('CLOSED'), ('PARTIALLY_FILLED');

-- Sample data

INSERT INTO users (username, email, password, first_name, last_name)
VALUES
    ('alice123', 'alice@example.com', 'hashedpassword1', 'Alice', 'Nguyen'),
    ('bob456', 'bob@example.com', 'hashedpassword2', 'Bob', 'Tran');

INSERT INTO currencies (code, name)
VALUES
    ('BTC', 'Bitcoin'),
    ('ETH', 'Ethereum'),
    ('USDT', 'Tether USD');

INSERT INTO wallets (user_id)
VALUES (1), (2); -- Alice and Bob

INSERT INTO wallet_balances (wallet_id, currency_id, amount)
VALUES
    (1, 1, 1.5),     -- BTC
    (1, 3, 10000.0); -- USDT

INSERT INTO wallet_balances (wallet_id, currency_id, amount)
VALUES
    (2, 2, 10.0),    -- ETH
    (2, 3, 5000.0);  -- USDT

INSERT INTO orders (user_id, base_currency_id, quote_currency_id, order_type_id, price, amount, filled, order_status_id)
VALUES (1, 1, 3, 2, 30000.00, 0.5, 0.5, 2); -- Filled = 0.5, CLOSED

INSERT INTO orders (user_id, base_currency_id, quote_currency_id, order_type_id, price, amount, filled, order_status_id)
VALUES (2, 1, 3, 1, 30000.00, 0.5, 0.5, 2); -- Filled = 0.5, CLOSED

INSERT INTO trades (buy_order_id, sell_order_id, price, amount)
VALUES (2, 1, 30000.00, 0.5);
