

insert into `sys_module` (`ID`, `DESCRIPTION`, `NAME`, `PRIORITY`, `URL`, `PARENT_ID`, `TYPE`, `ACTIONS`, `METHODS`) values('73','','后台管理','1','#','1','1','ht','htgl');
insert into `sys_module` (`ID`, `DESCRIPTION`, `NAME`, `PRIORITY`, `URL`, `PARENT_ID`, `TYPE`, `ACTIONS`, `METHODS`) values('74','','会员管理','1','/biz/backstage/bizmember/list','73','1','BizMember','view');
insert into `sys_module` (`ID`, `DESCRIPTION`, `NAME`, `PRIORITY`, `URL`, `PARENT_ID`, `TYPE`, `ACTIONS`, `METHODS`) values('75','','会员新增','1','/biz/backstage/bizmember/create','74','0','BizMember','save');
insert into `sys_module` (`ID`, `DESCRIPTION`, `NAME`, `PRIORITY`, `URL`, `PARENT_ID`, `TYPE`, `ACTIONS`, `METHODS`) values('76','','会员编辑','2','/biz/backstage/bizmember/update','74','0','BizMember','edit');
insert into `sys_module` (`ID`, `DESCRIPTION`, `NAME`, `PRIORITY`, `URL`, `PARENT_ID`, `TYPE`, `ACTIONS`, `METHODS`) values('77','','会员删除','3','/biz/backstage/bizmember/delete','74','0','BizMember','delete');


insert into `sys_code` (`ID`, `CODE_TYPE`, `TYPE_NAME`, `CODE_VALUE`, `CODE_NAME`, `CODE_DESC`, `CODE_STATE`, `ORD_NO`) values('11','ISVALIDATED','是否认证用户','1','是','','0','0');
insert into `sys_code` (`ID`, `CODE_TYPE`, `TYPE_NAME`, `CODE_VALUE`, `CODE_NAME`, `CODE_DESC`, `CODE_STATE`, `ORD_NO`) values('12','ISVALIDATED','是否认证用户','0','否','','0','1');
insert into `sys_code` (`ID`, `CODE_TYPE`, `TYPE_NAME`, `CODE_VALUE`, `CODE_NAME`, `CODE_DESC`, `CODE_STATE`, `ORD_NO`) values('13','COST_TYPE','资金类型','00','支付宝','','0','0');
insert into `sys_code` (`ID`, `CODE_TYPE`, `TYPE_NAME`, `CODE_VALUE`, `CODE_NAME`, `CODE_DESC`, `CODE_STATE`, `ORD_NO`) values('14','COST_TYPE','资金类型','01','银行卡','','0','1');
