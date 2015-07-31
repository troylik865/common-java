ALTER TABLE `biz_site_message` 
CHARACTER SET = DEFAULT , COLLATE = DEFAULT ,
ADD COLUMN `status` VARCHAR(45) NULL COMMENT '状态' AFTER `message_content`;

ALTER TABLE `biz_site_message` 
CHANGE COLUMN `status` `status` VARCHAR(45) CHARACTER SET 'utf8' COLLATE 'utf8_bin' NULL DEFAULT NULL COMMENT '状态  待审核：0   审核中：1  审核通过：2  审核被驳回：3' ;

ALTER TABLE `biz_site_message` 
CHARACTER SET = DEFAULT , COLLATE = DEFAULT ;

ALTER TABLE `biz_member_rank` 
ADD COLUMN `desc` VARCHAR(400) NULL COMMENT '大师评价' AFTER `rank`;
