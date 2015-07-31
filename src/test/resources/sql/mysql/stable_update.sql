ALTER TABLE `biz_trans_record`
	CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id' FIRST;
	
ALTER TABLE `biz_attach`
	ADD COLUMN `attach_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '附件路径';
	CHANGE COLUMN `id` `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'id' FIRST;
	
ALTER TABLE `biz_attach`
	ADD COLUMN `attach_path` VARCHAR(200) NULL DEFAULT NULL COMMENT '附件路径';

ALTER TABLE `biz_trans_record`
	ADD COLUMN `curr_trade` BIGINT(20) NULL DEFAULT NULL COMMENT '当天交易结果=（当日入金-当日出金-手续费+/-当日盈亏）';
	
ALTER TABLE `biz_trans_record`
	DROP COLUMN `last_day_value`,
	DROP COLUMN `lastest_value`,
	DROP COLUMN `totle_gains_and_losses`,
	DROP COLUMN `total_income`,
	DROP COLUMN `total_outcome`;
	
	
ALTER TABLE `biz_trans_record`
	ADD COLUMN `curr_value` BIGINT(20) NULL DEFAULT NULL COMMENT '当日权益',
	ADD COLUMN `last_day_value` BIGINT(20) NULL DEFAULT NULL COMMENT '上日权益';
	
	ALTER TABLE `biz_trans_record`
	ADD COLUMN `origion_value` BIGINT(20) NULL DEFAULT NULL COMMENT '初期资金' AFTER `last_day_value`;
	
ALTER TABLE `biz_trans_record`
	ADD COLUMN `status` VARCHAR(1) NULL DEFAULT NULL COMMENT '状态：I:初始;S:通过;F:退回' AFTER `extend_field`;
	
CREATE TABLE `BIZ_REVIEW_LOG` (
	`id` int(11) NOT NULL AUTO_INCREMENT,,
	`BIZ_ID` INT(11) NULL DEFAULT NULL COMMENT '业务Id',
	`BIZ_TYPE` VARCHAR(50) NULL DEFAULT NULL COMMENT '业务类型',
	`comment` VARCHAR(4000) NULL DEFAULT NULL COMMENT '审批记录',
	`param` VARCHAR(4000) NULL DEFAULT NULL COMMENT '扩展字段',
	`user_id` INT(11) NULL DEFAULT NULL COMMENT '操作用户Id',
	`gmt_create` DATETIME NULL DEFAULT NULL COMMENT '创建时间',
	`gmt_modified` DATETIME NULL DEFAULT NULL COMMENT '修改时间',
	PRIMARY KEY (`ID`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='业务审核记录';
