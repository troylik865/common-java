ALTER TABLE `biz_member` ADD COLUMN `is_validated` VARCHAR(10) NULL COMMENT '是否是认证用户'  
AFTER `gmt_modified` , ADD COLUMN `gmt_validated` DATETIME NULL COMMENT '通过验证时间'  AFTER `is_validated` ;
ALTER TABLE `biz_member` ADD COLUMN `gmt_last_login` DATETIME NULL  AFTER `gmt_validated` ;
ALTER TABLE `biz_link_message` ADD COLUMN `address` VARCHAR(200) NULL COMMENT '联系地址'  AFTER `gmt_modified` ;
ALTER TABLE `biz_member_search` ADD COLUMN `member_id` INT NULL  AFTER `gmt_modified` ;
ALTER TABLE `biz_member_search` CHANGE COLUMN `adress` `address` VARCHAR(200) CHARACTER SET 'latin1' NULL DEFAULT NULL COMMENT '地址'  ;
ALTER TABLE `biz_attention_record` CHANGE COLUMN `start_count` `star_count` VARCHAR(5) NULL DEFAULT NULL COMMENT '评分'  ;
ALTER TABLE `biz_member` CHANGE COLUMN `adress` `address` VARCHAR(200) NULL DEFAULT NULL COMMENT '地址'  , ADD COLUMN `attent_daily_cost` BIGINT NULL COMMENT '被关注每天所需要花费的资金'  AFTER `gmt_last_login` , ADD COLUMN `cost_type` VARCHAR(10) NULL COMMENT '资金类型'  AFTER `attent_daily_cost` ;
ALTER TABLE `biz_attention_record` CHANGE COLUMN `desc` `member_desc` VARCHAR(1000) NULL DEFAULT NULL COMMENT '对大师的评价 '  ;
ALTER TABLE `biz_attention_record` ADD COLUMN `attent_day_num` INT NULL COMMENT '关注的天数'  AFTER `gmt_modified` ;

ALTER TABLE `biz_attention_record` CHANGE COLUMN `daily_cost` `daily_cost` VARCHAR(100) NULL DEFAULT NULL COMMENT '每日花费'  , CHANGE COLUMN `attent_day_num` `attent_day_num` VARCHAR(100) NULL DEFAULT NULL COMMENT '关注的天数'  ;
ALTER TABLE `biz_attention_record` CHANGE COLUMN `daily_cost` `daily_cost` VARCHAR(100) NULL DEFAULT NULL COMMENT '每日花费  每变更一次加一个‘；’ 格式20;30;40'  , CHANGE COLUMN `attent_day_num` `attent_day_num` VARCHAR(100) NULL DEFAULT NULL COMMENT '关注的天数 每变更一次加一个‘；’ 格式5;6;4'  ;
ALTER TABLE `biz_member_rank` ADD COLUMN `invest_direction` VARCHAR(20) NULL DEFAULT NULL COMMENT '投资方向'  AFTER `member_id` ;
