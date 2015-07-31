/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.1.62-community : Database - platform
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`troy` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `platform`;

/*Table structure for table `sys_area` */

DROP TABLE IF EXISTS `sys_area`;

CREATE TABLE `sys_area` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `AREA_CODE` varchar(16) DEFAULT NULL COMMENT '区域编号',
  `AREA_NAME` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父级区域id',
  `AREA_LEVEL` char(1) DEFAULT NULL COMMENT '级别:1、区域；2、街道',
  `REMARK` varchar(255) DEFAULT NULL COMMENT '备注',
  `CONSUME_CODE` varchar(8) DEFAULT NULL COMMENT '消费点代码',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

/*Data for the table `sys_area` */

insert  into `sys_area`(`ID`,`AREA_CODE`,`AREA_NAME`,`PARENT_ID`,`AREA_LEVEL`,`REMARK`,`CONSUME_CODE`) values (1,'0','市级',NULL,'0','根节点，勿动！',''),(2,'01','东湖区',1,'1','',''),(3,'02','西湖区',1,'1','',''),(4,'0101','东湖街道',2,'2','',''),(5,'0201','西湖街道',3,'2','',''),(6,'0102','东湖街道2',2,'2','',''),(7,'0103','东湖街道3',2,'2','',''),(8,'0104','东湖街道4',2,'2','',''),(9,'0105','东湖街道5',2,'2','',''),(10,'0106','东湖街道6',2,'2','',''),(11,'0107','东湖街道7',2,'2','','');

/*Table structure for table `sys_code` */

DROP TABLE IF EXISTS `sys_code`;

CREATE TABLE `sys_code` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `CODE_TYPE` varchar(32) NOT NULL COMMENT '代码类别',
  `TYPE_NAME` varchar(255) DEFAULT NULL COMMENT '类别名称',
  `CODE_VALUE` varchar(32) NOT NULL COMMENT '代码值',
  `CODE_NAME` varchar(32) DEFAULT NULL COMMENT '代码名称',
  `CODE_DESC` varchar(255) DEFAULT NULL COMMENT '代码描述',
  `CODE_STATE` char(1) DEFAULT NULL COMMENT '状态（0正常1停用）',
  `ORD_NO` int(11) DEFAULT NULL COMMENT '序号',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=345 DEFAULT CHARSET=utf8;

/*Data for the table `sys_code` */

insert  into `sys_code`(`ID`,`CODE_TYPE`,`TYPE_NAME`,`CODE_VALUE`,`CODE_NAME`,`CODE_DESC`,`CODE_STATE`,`ORD_NO`) values (1,'MODULE_TYPE','菜单类型','1','显示菜单','pos机设备状态','0',1),(2,'MODULE_TYPE','菜单类型','0','操作动作','','0',0),(3,'SEX','性别','1','男','','0',1),(4,'SEX','性别','0','女','','0',0),(5,'STATE','状态','1','停用11','','0',1),(6,'STATE','状态','0','正常','','0',0),(7,'STATUS','用户状态','enabled','可用','','0',0),(8,'STATUS','用户状态','disabled','不可用','','0',1),(9,'AREA_LEVEL','区域级别','1','城区','','0',1),(10,'AREA_LEVEL','区域级别','2','街道','','0',2);

/*Table structure for table `sys_group` */

DROP TABLE IF EXISTS `sys_group`;

CREATE TABLE `sys_group` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `NAME` varchar(255) DEFAULT NULL COMMENT '组织机构名称',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父级id',
  `AREA_CODE` varchar(16) DEFAULT NULL COMMENT '所属城区',
  `STREET_CODE` varchar(16) DEFAULT NULL COMMENT '所属街道',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_group` */

insert  into `sys_group`(`ID`,`DESCRIPTION`,`NAME`,`PARENT_ID`,`AREA_CODE`,`STREET_CODE`) values (1,'不能删除，虚拟组织，提供根节点','根组织',NULL,'',''),(2,'','后台管理',1,'01','0101');

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '记录时间',
  `IP_ADDRESS` varchar(16) DEFAULT NULL COMMENT 'ip地址',
  `LOG_LEVEL` varchar(16) DEFAULT NULL COMMENT '日志级别',
  `MESSAGE` varchar(2000) DEFAULT NULL COMMENT '日志内容',
  `USERNAME` varchar(32) DEFAULT NULL COMMENT '操作用户',
  `LOG_TYPE` varchar(32) DEFAULT NULL COMMENT '日志类型',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

/*Data for the table `sys_log` */

/*Table structure for table `sys_module` */

DROP TABLE IF EXISTS `sys_module`;

CREATE TABLE `sys_module` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '描述',
  `NAME` varchar(32) DEFAULT NULL COMMENT '模块名称',
  `PRIORITY` int(10) DEFAULT NULL COMMENT '优先级',
  `URL` varchar(255) DEFAULT NULL COMMENT '请求地址',
  `PARENT_ID` bigint(20) DEFAULT NULL COMMENT '父级id',
  `TYPE` char(1) DEFAULT NULL COMMENT '菜单类型：1、显示菜单；0、操作动作',
  `ACTIONS` varchar(32) DEFAULT NULL COMMENT '功能操作名称',
  `METHODS` varchar(32) DEFAULT NULL COMMENT '功能方法名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=970 DEFAULT CHARSET=utf8;

/*Data for the table `sys_module` */

insert  into `sys_module`(`ID`,`DESCRIPTION`,`NAME`,`PRIORITY`,`URL`,`PARENT_ID`,`TYPE`,`ACTIONS`,`METHODS`) values (1,'所有模块的根节点，不能删除','根模块',1,'#',NULL,'1','',''),(2,'安全管理:包含权限管理、模块管理、组织管理、角色管理','安全管理',1,'#',23,'1','System','Security'),(3,'','用户管理',1,'/system/security/user/list',2,'1','User','view'),(4,'','角色管理',2,'/system/security/role/list',2,'1','Role','view'),(5,'','模块管理',3,'/system/security/module/tree',2,'1','Module','view'),(7,'','组织管理',4,'/system/security/group/tree',2,'1','Group','view'),(9,'','缓存管理',99,'/system/security/cacheManage/index',2,'1','CacheManage','view'),(10,'','添加用户',99,'/system/security/user/create',3,'0','User','save'),(11,'','编辑用户',99,'/system/security/user/update',3,'0','User','edit'),(12,'','删除用户',99,'/system/security/user/delete',3,'0','User','delete'),(13,'','添加角色',99,'/system/security/role/create',4,'0','Role','save'),(14,'','删除角色',99,'/system/security/role/delete',4,'0','Role','delete'),(15,'','编辑角色',99,'/system/security/role/update',4,'0','Role','edit'),(16,'','添加组织',99,'/system/security/group/create',7,'0','Group','save'),(17,'','编辑组织',99,'/system/security/group/update',7,'0','Group','edit'),(18,'','删除组织',99,'/system/security/group/delete',7,'0','Group','delete'),(19,'','添加模块',99,'/system/security/module/create',5,'0','Module','save'),(20,'','编辑模块',99,'/system/security/module/update',5,'0','Module','edit'),(21,'','删除模块',99,'/system/security/module/delete',5,'0','Module','delete'),(22,'','清理缓存',99,'/system/security/cacheManage/update',9,'0','CacheManage','edit'),(23,'','系统管理',1,'#',1,'1','Index','sysmgr'),(28,'','系统日志',2,'/system/config/log/list',68,'1','Log','view'),(29,'','删除日志',99,'/system/config/log/delete',28,'0','Log','delete'),(30,'','系统参数',3,'/system/config/para/list',68,'1','Para','view'),(31,'','添加参数',99,'/system/config/para/create',30,'0','Para','save'),(32,'','编辑参数',99,'/system/config/para/update',30,'0','Para','edit'),(33,'','删除参数',99,'/system/config/para/delete',30,'0','Para','delete'),(68,'','系统设置',2,'#',23,'1','System','Config'),(69,'','数据字典',1,'/system/config/code/list',68,'1','Code','view'),(70,'','添加代码',99,'/system/config/code/create',69,'0','Code','save'),(71,'','编辑代码',99,'/system/config/code/update',69,'0','Code','edit'),(72,'','删除代码',99,'/system/config/code/delete',69,'0','Code','delete');

/*Table structure for table `sys_para` */

DROP TABLE IF EXISTS `sys_para`;

CREATE TABLE `sys_para` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `PARA_KEY` varchar(32) DEFAULT NULL COMMENT '参数关键字',
  `PARA_VALUE` varchar(1024) DEFAULT NULL COMMENT '参数值',
  `PARA_NAME` varchar(32) DEFAULT NULL COMMENT '参数名称',
  `PARA_DESC` varchar(128) DEFAULT NULL COMMENT '参数描述',
  `PARA_STATE` char(1) DEFAULT NULL COMMENT '状态（0正常1停用）',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Data for the table `sys_para` */

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `DESCRIPTION` varchar(255) DEFAULT NULL COMMENT '角色描述',
  `NAME` varchar(64) DEFAULT NULL COMMENT '角色名称',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`ID`,`DESCRIPTION`,`NAME`) values (1,'最高级别，非技术人员勿动','超级管理员');

/*Table structure for table `sys_role_module` */

DROP TABLE IF EXISTS `sys_role_module`;

CREATE TABLE `sys_role_module` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ROLE_ID` bigint(20) DEFAULT NULL COMMENT '角色id',
  `MODULE_ID` bigint(20) DEFAULT NULL COMMENT '模块id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1048 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_module` */

insert  into `sys_role_module`(`ID`,`ROLE_ID`,`MODULE_ID`) values (1,1,2),(2,1,3),(3,1,4),(4,1,5),(5,1,7),(7,1,9),(8,1,10),(9,1,11),(10,1,12),(11,1,13),(12,1,14),(13,1,15),(14,1,16),(15,1,17),(16,1,18),(17,1,19),(18,1,20),(19,1,21),(20,1,22),(26,1,23),(28,1,30),(29,1,31),(30,1,32),(31,1,33),(61,1,68),(62,1,69),(63,1,70),(64,1,72),(65,1,71),(81,1,28),(82,1,29);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `REALNAME` varchar(64) DEFAULT NULL COMMENT '真实姓名',
  `USERNAME` varchar(32) DEFAULT NULL COMMENT '登录用户名',
  `PASSWORD` varchar(64) DEFAULT NULL COMMENT 'MD5(用户密码+随机码)',
  `SALT` varchar(32) DEFAULT NULL COMMENT '随机码',
  `CREATE_TIME` datetime DEFAULT NULL COMMENT '创建时间',
  `STATUS` varchar(20) DEFAULT NULL COMMENT '激活状态',
  `EMAIL` varchar(128) DEFAULT NULL COMMENT '邮箱',
  `PHONE` varchar(32) DEFAULT NULL COMMENT '电话',
  `GROUP_ID` bigint(20) DEFAULT NULL COMMENT '所属机构id',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`ID`,`REALNAME`,`USERNAME`,`PASSWORD`,`SALT`,`CREATE_TIME`,`STATUS`,`EMAIL`,`PHONE`,`GROUP_ID`) values (1,'超级管理员','admin','b3d0445a1634f18e6d366c0b2c2af2a4','24b5a45df3dd321d','2013-06-01 12:00:01','enabled','','13988888888',2);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `ROLE_ID` bigint(20) DEFAULT NULL COMMENT '角色id',
  `USER_ID` bigint(20) DEFAULT NULL COMMENT '用户id',
  `PRIORITY` int(10) DEFAULT NULL COMMENT '优先级',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`ID`,`ROLE_ID`,`USER_ID`,`PRIORITY`) values (1,1,1,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
