CREATE TABLE `biz_attach` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `attach_real_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '附件真正的名称',
  `attach_name` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '附件名称',
  `attach_type` varchar(40) CHARACTER SET utf8 DEFAULT NULL COMMENT '主要是附件关联的业务类型',
  `ref_id` int(11) DEFAULT NULL COMMENT '附件关联的业务的数据ID\n',
  `attach_size` int(11) DEFAULT NULL COMMENT '附件大小',
  `cost_type` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '花费的类型',
  `value` bigint(20) DEFAULT NULL COMMENT '对应的花费值',
  `extend_field` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `attach_path` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '附件路径',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='附件表';

CREATE TABLE `biz_attention_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `attented_member_id` int(11) DEFAULT NULL COMMENT '被关注的会员ID（大师i）',
  `type` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '资金类型\n',
  `value` bigint(20) DEFAULT NULL COMMENT '具体资金类型下面的值',
  `daily_cost` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '每日花费  每变更一次加一个‘；’ 格式20;30;40',
  `star_count` varchar(5) CHARACTER SET utf8 DEFAULT NULL COMMENT '评分',
  `member_desc` varchar(1000) CHARACTER SET utf8 DEFAULT NULL COMMENT '对大师的评价 ',
  `extend_field` varchar(2000) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `attent_day_num` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '关注的天数 每变更一次加一个‘；’ 格式5;6;4',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='大师关注记录';

CREATE TABLE `biz_column_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `item_name` varchar(80) CHARACTER SET utf8 DEFAULT NULL COMMENT '栏目名称',
  `item_type` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '栏目类型',
  `show_position` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '显示位置',
  `item_content_type` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '栏目内容类型   能下载的附件/超链接',
  `item_content` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '栏目内容 如果是类目类型是 1. 超链接： 超链接的链接地址 2. 附件：  附件Id',
  `rank` int(11) DEFAULT NULL COMMENT '排序',
  `extend_field` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='栏目表';

CREATE TABLE `biz_finance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `type` varchar(10) CHARACTER SET utf8 DEFAULT NULL,
  `value` bigint(20) DEFAULT NULL COMMENT '具体的值',
  `extend_field` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段\n',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资金相关的实体';

CREATE TABLE `biz_finance_trans_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id\n',
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `type` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '资金类型',
  `value` bigint(20) DEFAULT NULL COMMENT '资金类型对应的值',
  `account_balance` bigint(20) DEFAULT NULL COMMENT '账户余额',
  `trans_use` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '交易用途',
  `trans_type` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '交易类型，主要记录资金的出/入',
  `trans_use_id` int(11) DEFAULT NULL COMMENT '交易用途对应的业务逻辑Id',
  `extend_field` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='资金交易相关的实体';

CREATE TABLE `biz_investion` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `invest_direction` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '投资方向',
  `is_validated` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否通过参赛验证',
  `gmt_validated` datetime DEFAULT NULL COMMENT '通过验证的时间',
  `extend_field` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='投资方向表，记录每一个会员所有的投资方向信息及相关的参赛状态';

CREATE TABLE `biz_link_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '留言Id',
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '姓名',
  `sex` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `phone_number` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号码',
  `message` varchar(1000) CHARACTER SET utf8 DEFAULT NULL COMMENT '信息内容',
  `extend_field` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `address` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '联系地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='留言信息 中文';

CREATE TABLE `biz_login_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `gmt_login` datetime DEFAULT NULL COMMENT '登陆时间',
  `extend_field` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员登陆历史信息';

CREATE TABLE `biz_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_no` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '会员编号',
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '姓名',
  `sex` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `phone_number` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号码',
  `email` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT 'QQ',
  `invest_direction` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '投资方向',
  `user_name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '密码',
  `address` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '地址',
  `extend_field` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `is_validated` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否是认证用户',
  `gmt_validated` datetime DEFAULT NULL COMMENT '通过验证时间',
  `gmt_last_login` datetime DEFAULT NULL,
  `attent_daily_cost` int(19) DEFAULT NULL COMMENT '被关注每天所需要花费的资金 ',
  `cost_type` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '资金类型',
  `member_link` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '会员链接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员表';

CREATE TABLE `biz_member_rank` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `invest_direction` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '投资方向',
  `rank` int(11) DEFAULT NULL COMMENT '排名',
  `desc` varchar(400) COLLATE utf8_bin DEFAULT NULL COMMENT '大师评价',
  `extend_field` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='会员排名表 （主要是针对大师）';

CREATE TABLE `biz_member_search` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '姓名',
  `sex` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '性别\n',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `phone_number` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号码',
  `email` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '邮箱',
  `qq` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT 'QQ',
  `invest_direction` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '投资方向',
  `address` varchar(200) CHARACTER SET latin1 DEFAULT NULL COMMENT '地址',
  `highest_trans_account_value` int(11) DEFAULT NULL COMMENT '最高交易账户',
  `intent_trans_account_value` int(11) DEFAULT NULL COMMENT '意向交易账户',
  `extend_field` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `member_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='大师搜索记录';

CREATE TABLE `biz_mobile_msg` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone_number` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '手机号',
  `msg_content` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '短信内容',
  `msg_use` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '短信用途',
  `extend_field` varchar(100) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL,
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='短信发送记录表';

CREATE TABLE `biz_publish_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `content` varchar(1000) CHARACTER SET utf8 DEFAULT NULL COMMENT '发布内容',
  `extend_field` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='实盘信息发布';

CREATE TABLE `biz_publish_message_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL COMMENT '消息Id',
  `accept_member_id` int(11) DEFAULT NULL COMMENT '接收消息的会员Id',
  `status` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '消息接收的情况 查看/未查看',
  `extend_field` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `publish_member_id` int(11) DEFAULT NULL COMMENT '发布信息的会员Id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='实盘信息发布详细';

CREATE TABLE `biz_review_log` (
  `ID` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `BIZ_ID` int(11) DEFAULT NULL COMMENT '业务Id',
  `BIZ_TYPE` varchar(50) DEFAULT NULL COMMENT '业务类型',
  `comment` varchar(4000) DEFAULT NULL COMMENT '审批记录',
  `param` varchar(4000) DEFAULT NULL COMMENT '扩展字段',
  `user_id` int(11) DEFAULT NULL COMMENT '操作用户Id',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `id_UNIQUE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COMMENT='业务审核记录';

CREATE TABLE `biz_site_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL COMMENT '信息发起的会员Id',
  `related_member_id` int(11) DEFAULT NULL COMMENT '信息相关会员的Id',
  `receive_member_id` int(11) DEFAULT NULL COMMENT '接收信息的会员Id',
  `message_type` varchar(40) CHARACTER SET utf8 DEFAULT NULL COMMENT '站内信息的类型\n',
  `message_content` varchar(1000) CHARACTER SET utf8 DEFAULT NULL COMMENT '信息的内容',
  `status` varchar(45) COLLATE utf8_bin DEFAULT NULL COMMENT '状态  待审核：0   审核中：1  审核通过：2  审核被驳回：3',
  `extend_field` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='站内信，目前主要包括大师的举报信息，大师排名显示和参赛申请';

CREATE TABLE `biz_spread_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `spread_member_id` int(11) DEFAULT NULL COMMENT '推广链接对应的用户Id',
  `regist_member_id` int(11) DEFAULT NULL COMMENT '想关联的注册用户的Id',
  `extend_field` varchar(400) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='记录推广记录的表';

CREATE TABLE `biz_trans_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `invest_type` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '投资品种',
  `import_date` varchar(30) CHARACTER SET utf8 DEFAULT NULL COMMENT '导入时间 yyyy-mm-dd',
  `record_type` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '记录类型 ，主要记录是否是第一次导入',
  `fee` bigint(20) DEFAULT NULL COMMENT '手续费',
  `gains_and_losses` bigint(20) DEFAULT NULL COMMENT '当日盈亏',
  `curr_income` bigint(20) DEFAULT NULL COMMENT '当日入金',
  `curr_outcome` bigint(20) DEFAULT NULL COMMENT '当日出金',
  `extend_field` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `status` varchar(1) COLLATE utf8_bin DEFAULT NULL COMMENT '状态：I:初始;S:通过;F:退回',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `curr_trade` bigint(20) DEFAULT NULL COMMENT '当天交易结果=（当日入金-当日出金-手续费+/-当日盈亏）',
  `curr_value` bigint(20) DEFAULT NULL COMMENT '当日权益',
  `last_day_value` bigint(20) DEFAULT NULL COMMENT '上日权益',
  `origion_value` bigint(20) DEFAULT NULL COMMENT '初期资金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='交易记录';

CREATE TABLE `biz_trans_record_statis` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `invest_type` varchar(20) CHARACTER SET utf8 DEFAULT NULL COMMENT '投资品种',
  `origion_value` bigint(20) DEFAULT NULL COMMENT '初期资金',
  `lastest_value` bigint(20) DEFAULT NULL COMMENT '最新权益',
  `total` bigint(20) DEFAULT NULL COMMENT '累计盈亏',
  `total_income` bigint(20) DEFAULT NULL COMMENT '累计入金',
  `total_outcome` bigint(20) DEFAULT NULL COMMENT '累计出金',
  `extend_field` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `is_validated` varchar(10) CHARACTER SET utf8 DEFAULT NULL COMMENT '是否参赛，冗余字段',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='交易记录统计表，每个大师只会有一条记录';

CREATE TABLE `biz_visit_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `member_id` int(11) DEFAULT NULL COMMENT '会员Id',
  `attented_member_id` int(11) DEFAULT NULL COMMENT '被关注的大师记录',
  `gmt_visit` datetime DEFAULT NULL COMMENT '访问时间',
  `extend_field` varchar(200) CHARACTER SET utf8 DEFAULT NULL COMMENT '扩展字段',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='访问记录（主要针对大师）';


CREATE TABLE `troy`.`biz_news` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '唯一标示',
  `title` VARCHAR(200) NULL COMMENT '新闻标题',
  `content` VARCHAR(2000) NULL COMMENT '新闻内容',
  `extend_field` VARCHAR(200) NULL COMMENT '扩展字段',
  `gmt_create` DATETIME NULL COMMENT '创建时间',
  `gmt_modified` DATETIME NULL COMMENT '修改时间',
  PRIMARY KEY (`id`))
COMMENT = '新闻表';
