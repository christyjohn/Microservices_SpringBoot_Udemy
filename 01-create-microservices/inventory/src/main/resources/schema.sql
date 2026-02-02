--
-- Table structure for table `customers`
--

CREATE TABLE IF NOT EXISTS `customers` (
    `id` int NOT NULL AUTO_INCREMENT,
    `first_name` varchar(100) NOT NULL,
    `last_name` varchar(100) NOT NULL,
    `email` varchar(100) NOT NULL,
    `mobile_number` varchar(20) NOT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    ` updated_by` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

--
-- Table structure for table `customers`
--

CREATE TABLE IF NOT EXISTS `products` (
    `id` int NOT NULL AUTO_INCREMENT,
    `product_name`varchar(45) NOT NULL,
    `sku` VARCHAR(100) NOT NULL,
    `price` DECIMAL(10, 2) NOT NULL,
    `product_description` varchar(250) NOT NULL,
    `created_at` date NOT NULL,
    `created_by` varchar(20) NOT NULL,
    `updated_at` date DEFAULT NULL,
    `updated_by` varchar(20) DEFAULT NULL,
    PRIMARY KEY (`id`)
);