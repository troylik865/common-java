CREATE TABLE `biz_trans_record_statis` (
  `id` int(11) NOT NULL COMMENT 'id',
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `type` varchar(20) DEFAULT NULL COMMENT '投资品种',
  `origion_value` bigint(20) DEFAULT NULL COMMENT '初期资金',
  `lastest_value` bigint(20) DEFAULT NULL COMMENT '最新权益',
  `totle_gains_and_losses` bigint(20) DEFAULT NULL COMMENT '累计盈亏',
  `total_income` bigint(20) DEFAULT NULL COMMENT '累计入金',
  `total_outcome` bigint(20) DEFAULT NULL COMMENT '累计出金',
  `extend_field` varchar(200) DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='交易记录统计表，每个大师只会有一条记录';

ALTER TABLE `biz_trans_record` 
CHANGE COLUMN `type` `invest_type` VARCHAR(20) NULL DEFAULT NULL COMMENT '投资品种' ;

ALTER TABLE `biz_investion` 
CHANGE COLUMN `is_validated` `is_validated` VARCHAR(10) NULL DEFAULT NULL COMMENT '是否通过参赛验证' ;

ALTER TABLE `biz_trans_record_statis` 
ADD COLUMN `is_validated` VARCHAR(10) NULL COMMENT '是否参赛，冗余字段' AFTER `gmt_modified`;


CREATE TABLE `troy`.`biz_investion` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` INT NULL COMMENT '会员Id',
  `invest_direction` VARCHAR(20) NULL COMMENT '投资方向',
  `is_validated` DATETIME NULL COMMENT '是否通过参赛验证',
  `gmt_validated` DATETIME NULL COMMENT '通过验证的时间',
  `extend_field` VARCHAR(100) NULL COMMENT '扩展字段',
  `gmt_create` DATETIME NULL COMMENT '创建时间',
  `gmt_modified` DATETIME NULL COMMENT '修改时间',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci
COMMENT = '投资方向表，记录每一个会员所有的投资方向信息及相关的参赛状态';


ALTER TABLE `biz_trans_record_statis` 
CHANGE COLUMN `type` `invest_type` VARCHAR(20) NULL DEFAULT NULL COMMENT '投资品种' ;

ALTER TABLE `biz_trans_record_statis` 
CHANGE COLUMN `totle_gains_and_losses` `total` BIGINT(20) NULL DEFAULT NULL COMMENT '累计盈亏' ;


CREATE TABLE `biz_mobile_msg` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `phone_number` VARCHAR(45) NULL COMMENT '手机号',
  `msg_content` VARCHAR(400) NULL COMMENT '短信内容',
  `msg_use` VARCHAR(45) NULL COMMENT '短信用途',
  `biz_mobile_msgcol` VARCHAR(45) NULL,
  `extend_field` VARCHAR(100) NULL COMMENT '扩展字段',
  `gmt_create` DATETIME NULL,
  `gmt_modified` DATETIME NULL COMMENT '修改时间',
  PRIMARY KEY (`id`))
COMMENT = '短信发送记录表';



