 -- customers

INSERT INTO `customer` (`name`, `email`, `mobile_number`, `created_at`, `created_by`)
VALUES ('Joe Bloggs', 'joe@example.com', '0871234567', CURRENT_DATE, 'ADMIN');

INSERT INTO `customer` (`name`, `email`, `mobile_number`, `created_at`, `created_by`)
VALUES ('Jane Doe', 'jane@example.com', '0861112233', CURRENT_DATE, 'ADMIN');

INSERT INTO `customer` (`name`, `email`, `mobile_number`, `created_at`, `created_by`)
VALUES ('Alice Smith', 'alice@example.com', '0859998877', CURRENT_DATE, 'ADMIN');

--- orders

INSERT INTO `orders` (`customer_id`, `serial_number`, `quantity`, `status`, `created_at`, `created_by`)
VALUES (1, 'FEN12345678', 1, 'Pending', CURRENT_DATE, 'ADMIN');

INSERT INTO `orders` (`customer_id`, `serial_number`, `quantity`, `status`, `created_at`, `created_by`)
VALUES (2, 'GIB87654321', 1, 'Shipped', CURRENT_DATE, 'ADMIN');

INSERT INTO `orders` (`customer_id`, `serial_number`, `quantity`, `status`, `created_at`, `created_by`)
VALUES (3, 'PRS55443322', 2, 'Delivered', CURRENT_DATE, 'ADMIN');