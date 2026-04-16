CREATE TABLE IF NOT EXISTS `inventory` (
  `guitar_id` int AUTO_INCREMENT PRIMARY KEY,
  `model_name` varchar(100) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `serial_number` varchar(100) NOT NULL,
  `price` int NOT NULL,
  `total_stock` int NOT NULL,
  `available_stock` int NOT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL
);