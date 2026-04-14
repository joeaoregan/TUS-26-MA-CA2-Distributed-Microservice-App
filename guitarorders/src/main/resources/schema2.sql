CREATE TABLE IF NOT EXISTS `guitar` (
  `guitar_id` int AUTO_INCREMENT PRIMARY KEY,
  `model_name` varchar(100) NOT NULL,
  `brand` varchar(100) NOT NULL,
  `stock_count` int NOT NULL,
  `price` double NOT NULL,
  `created_at` date NOT NULL,
  `created_by` varchar(20) NOT NULL,
  `updated_at` date DEFAULT NULL,
  `updated_by` varchar(20) DEFAULT NULL
);