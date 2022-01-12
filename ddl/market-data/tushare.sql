CREATE TABLE `trade_cal` (
 `id` BIGINT(20) UNSIGNED auto_increment COMMENT '主键',
 `exchange` VARCHAR(63) NOT NULL COMMENT '交易所',
 `cal_date` DATE NOT NULL COMMENT '交易日期',
 `is_open` tinyint(1) NOT NULL COMMENT '是否开盘',
 `pretrade_date` DATE NULL COMMENT '前一个交易日',
 PRIMARY KEY(`id`)
) ENGINE=InnoDB COMMENT = '交易日历';

CREATE TABLE `stock_basic` (
    `id` BIGINT(20) UNSIGNED auto_increment COMMENT '主键',
    `symbol` VARCHAR(20) NOT NULL COMMENT '股票代码',
    `name` VARCHAR(20) NOT NULL COMMENT '股票名称',
    `area` VARCHAR(20)  NULL COMMENT '地域',
    `industry` VARCHAR(20)  NULL COMMENT '所属行业',
    `fullname` VARCHAR(255) NOT NULL COMMENT '股票全称',
    `enname` VARCHAR(255) NOT NULL COMMENT '英文全称',
    `cnspell` VARCHAR(20) NOT NULL COMMENT '拼音缩写',
    `market` VARCHAR(20) NOT NULL COMMENT '市场类型',
    `exchange` VARCHAR(20) NOT NULL COMMENT '交易所',
    `curr_type` VARCHAR(20) NOT NULL COMMENT '交易货币',
    `list_status` VARCHAR(2) NOT NULL COMMENT '上市状态 L上市 D退市 P暂停上市',
    `list_date` DATE NOT NULL COMMENT '上市日期',
    `delist_date` DATE  NULL COMMENT '退市日期',
    `is_hs` VARCHAR(2) NOT NULL COMMENT '是否沪深港通标的，N否 H沪股通 S深股通',
    INDEX `idx_symbol` (`symbol`) USING BTREE,
     PRIMARY KEY(`id`)
) ENGINE=InnoDB COMMENT = '基础信息';

CREATE TABLE `day_data` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  `symbol` varchar(20) NOT NULL COMMENT '股票代码',
  `trade_date` date NOT NULL COMMENT '交易日期',
  `open` decimal(6,2) NOT NULL COMMENT '开盘价',
  `high` decimal(6,2) NOT NULL COMMENT '最高价',
  `low` decimal(6,2) NOT NULL COMMENT '最低价',
  `close` decimal(6,2) NOT NULL COMMENT '收盘价',
  `pre_close` decimal(6,2) DEFAULT NULL COMMENT '昨收价',
  `change_price` decimal(6,2) NOT NULL COMMENT '涨跌额',
  `pct_chg` decimal(10,6) NOT NULL COMMENT '涨跌幅',
  `vol` decimal(15,2) NULL COMMENT '成交量',
  `amount` decimal(20,5) NULL COMMENT '成交额',
  `back_adjust` decimal(10,5)  NULL COMMENT '后复权因子',
  `font_adjust` decimal(10,5)  NULL COMMENT '前复权因子',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4  COMMENT='日线行情数据'


CREATE TABLE `week_data` (
`id` BIGINT(20) UNSIGNED auto_increment COMMENT '主键',
`symbol` VARCHAR(20) NOT NULL COMMENT '股票代码',
`trade_date` DATE NOT NULL COMMENT '交易日期',
`open` decimal(6, 2) NOT NULL COMMENT '开盘价',
`high` decimal(6, 2) NOT NULL COMMENT '最高价',
`low` decimal(6, 2) NOT NULL COMMENT '最低价',
`close` decimal(6, 2) NOT NULL COMMENT '收盘价',
`pre_close` decimal(6, 2) NOT NULL COMMENT '昨收价',
`change` decimal(6, 2) NOT NULL COMMENT '涨跌额',
`pct_chg` decimal(6, 6) NOT NULL COMMENT '涨跌幅',
`vol` decimal(15, 2) NOT NULL COMMENT '成交量',
`amount` decimal(15, 2) NOT NULL COMMENT '成交额',
unique INDEX `uniq_symbol_date` (`symbol`, `trade_date`) USING BTREE,
PRIMARY KEY(`id`)
) ENGINE=InnoDB COMMENT = '周线行情数据';

CREATE TABLE `syn_task` (
    `id` BIGINT(20) UNSIGNED auto_increment COMMENT '主键',
    `name` VARCHAR(20) NOT NULL COMMENT '任务标识',
    `type` VARCHAR(20) NOT NULL COMMENT '任务类型',
    `start_time` datetime null COMMENT '开始时间',
    `end_time` datetime null COMMENT '结束时间',
    `state` VARCHAR(20) NULL COMMENT '任务状态',
    `error_log` VARCHAR(255) NULL COMMENT '错误信息',
    unique INDEX `uniq_name_type` (`name`, `type`) USING BTREE,
    PRIMARY KEY(`id`)
)  ENGINE=InnoDB COMMENT = '任务日志';


