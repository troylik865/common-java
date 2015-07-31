CREATE TABLE `biz_spread_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spread_member_id` int(11) DEFAULT NULL COMMENT '推广链接对应的用户Id',
  `regist_member_id` int(11) DEFAULT NULL COMMENT '想关联的注册用户的Id',
  `extend_field` varchar(400) DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='记录推广记录的表';


CREATE TABLE `biz_site_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL COMMENT '信息发起的会员Id',
  `related_member_id` int(11) DEFAULT NULL COMMENT '信息相关会员的Id',
  `receive_member_id` int(11) DEFAULT NULL COMMENT '接收信息的会员Id',
  `message_type` varchar(40) DEFAULT NULL COMMENT '站内信息的类型',
  `message_content` varchar(1000) DEFAULT NULL COMMENT '信息的内容',
  `extend_field` varchar(400) DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='站内信，目前主要包括大师的举报信息，大师排名显示和参赛申请等';


ALTER TABLE `biz_publish_message_detail` CHANGE COLUMN `messageId` `message_id` INT(11) NULL DEFAULT NULL COMMENT '消息Id'  , ADD COLUMN `publish_member_id` INT NULL COMMENT '发布信息的会员Id'  AFTER `gmt_modified` ;
ALTER TABLE `biz_member` ADD COLUMN `member_link` VARCHAR(400) NULL COMMENT '会员链接'  AFTER `cost_type` ;
ALTER TABLE `biz_finance_trans_detail` ADD COLUMN `account_balance` BIGINT(20) NULL COMMENT '账户余额'  AFTER `value` ;
ALTER TABLE `biz_attention_record` CHANGE COLUMN `extend_field` `extend_field` VARCHAR(2000) NULL DEFAULT NULL COMMENT '扩展字段'  ;



