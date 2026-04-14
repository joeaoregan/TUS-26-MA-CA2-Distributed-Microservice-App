CREATE TABLE IF NOT EXISTS `customer` (
  `customer_id` int AUTO_INCREMENT PRIMARY KEY,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `mobile_number` varchar(20) NOT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `guitar_orders` (
  `order_id` int AUTO_INCREMENT PRIMARY KEY,
  `customer_id` int NOT NULL,
  `guitar_id` int NOT NULL,   -- Links to Inventory
  `quantity` int NOT NULL,
  `status` varchar(20) NOT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL,
  FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);