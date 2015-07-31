ALTER TABLE `biz_trans_record` 
ADD COLUMN `gmt_validate` DATETIME NULL AFTER `total_gains_and_losses`;

ALTER TABLE `biz_publish_message` 
ADD COLUMN `is_validated` VARCHAR(2) NULL COMMENT '是否通过验证' AFTER `gmt_modified`,
ADD COLUMN `gmt_validated` DATETIME NULL COMMENT '通过验证的时间' AFTER `is_validated`;


ALTER TABLE `biz_publish_message` 
CHANGE COLUMN `is_validated` `status` VARCHAR(2) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL COMMENT '状态 成功为S 失败F 审核中和初始化I' ;


CREATE TABLE `biz_news` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '唯一标示',
  `title` VARCHAR(200) NULL COMMENT '新闻标题',
  `content` VARCHAR(2000) NULL COMMENT '新闻内容',
  `extend_field` VARCHAR(200) NULL COMMENT '扩展字段',
  `gmt_create` DATETIME NULL COMMENT '创建时间',
  `gmt_modified` DATETIME NULL COMMENT '修改时间',
  PRIMARY KEY (`id`))
COMMENT = '新闻表';

ALTER TABLE `biz_news` 
ADD COLUMN `turn` INT NULL AFTER `content`;

