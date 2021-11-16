CREATE TABLE `stock_base_info` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT,
    `db_create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '����ʱ��',
    `db_modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '����ʱ��',
    `code` varchar(10) NOT NULL,
    `name` varchar(10) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;