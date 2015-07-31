prompt PL/SQL Developer import file
prompt Created on 2013年6月21日 by Administrator
set feedback off
set define off
prompt Creating SYS_AREA...
create table SYS_AREA
(
  ID           NUMBER(20) not null,
  AREA_CODE    VARCHAR2(16),
  AREA_NAME    VARCHAR2(50),
  PARENT_ID    NUMBER(20),
  AREA_LEVEL   CHAR(1),
  REMARK       VARCHAR2(255),
  CONSUME_CODE VARCHAR2(8)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 16K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_AREA
  is '行政区划表';
comment on column SYS_AREA.AREA_CODE
  is '区域编号';
comment on column SYS_AREA.AREA_NAME
  is '区域名称';
comment on column SYS_AREA.PARENT_ID
  is '父级区域id';
comment on column SYS_AREA.AREA_LEVEL
  is '级别:1、区域；2、街道';
comment on column SYS_AREA.REMARK
  is '备注';
comment on column SYS_AREA.CONSUME_CODE
  is '消费点代码';
alter table SYS_AREA
  add constraint PK_SYS_AREA_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for SYS_AREA...
alter table SYS_AREA disable all triggers;
prompt Deleting SYS_AREA...
delete from SYS_AREA;
commit;
prompt Loading SYS_AREA...
insert into SYS_AREA (ID, AREA_CODE, AREA_NAME, PARENT_ID, AREA_LEVEL, REMARK, CONSUME_CODE)
values (1, '00', '市级', null, '0', '根节点，勿动！', null);
insert into SYS_AREA (ID, AREA_CODE, AREA_NAME, PARENT_ID, AREA_LEVEL, REMARK, CONSUME_CODE)
values (2, '01', '东湖区', 1, '1', null, null);
insert into SYS_AREA (ID, AREA_CODE, AREA_NAME, PARENT_ID, AREA_LEVEL, REMARK, CONSUME_CODE)
values (3, '02', '西湖区', 1, '1', null, null);
insert into SYS_AREA (ID, AREA_CODE, AREA_NAME, PARENT_ID, AREA_LEVEL, REMARK, CONSUME_CODE)
values (4, '0101', '东湖街道', 2, null, null, null);
insert into SYS_AREA (ID, AREA_CODE, AREA_NAME, PARENT_ID, AREA_LEVEL, REMARK, CONSUME_CODE)
values (5, '0201', '西湖街道', 3, '2', null, null);
commit;
prompt 5 records loaded
prompt Enabling triggers for SYS_AREA...
alter table SYS_AREA enable all triggers;
set feedback on
set define on
prompt Done.

prompt PL/SQL Developer import file
prompt Created on 2013年6月8日 by Administrator
set feedback off
set define off
prompt Creating SYS_CODE...
create table SYS_CODE
(
  ID         NUMBER(20) not null,
  CODE_TYPE  VARCHAR2(32) not null,
  TYPE_NAME  VARCHAR2(255),
  CODE_VALUE VARCHAR2(32) not null,
  CODE_NAME  VARCHAR2(32),
  CODE_DESC  VARCHAR2(255),
  CODE_STATE CHAR(1),
  ORD_NO     NUMBER
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_CODE
  is '系统：数据字典表';
comment on column SYS_CODE.ID
  is '主键id';
comment on column SYS_CODE.CODE_TYPE
  is '代码类别';
comment on column SYS_CODE.TYPE_NAME
  is '类别名称';
comment on column SYS_CODE.CODE_VALUE
  is '代码值';
comment on column SYS_CODE.CODE_NAME
  is '代码名称';
comment on column SYS_CODE.CODE_DESC
  is '代码描述';
comment on column SYS_CODE.CODE_STATE
  is '状态（0正常1停用）';
comment on column SYS_CODE.ORD_NO
  is '序号';
alter table SYS_CODE
  add constraint PK_SYS_CODE_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_CODE
  add constraint UI_SYS_CODE_TYPE_VALUE unique (CODE_TYPE, CODE_VALUE)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
prompt Creating SYS_PARA...
create table SYS_PARA
(
  ID         NUMBER(20) not null,
  PARA_KEY   VARCHAR2(32),
  PARA_VALUE VARCHAR2(1024),
  PARA_NAME  VARCHAR2(32),
  PARA_DESC  VARCHAR2(128),
  PARA_STATE  CHAR(1)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_PARA
  is '系统-参数表';
comment on column SYS_PARA.ID
  is '主键id';
comment on column SYS_PARA.PARA_KEY
  is '参数关键字';
comment on column SYS_PARA.PARA_VALUE
  is '参数值';
comment on column SYS_PARA.PARA_NAME
  is '参数名称';
comment on column SYS_PARA.PARA_DESC
  is '参数描述';
comment on column SYS_PARA.PARA_STATE
  is '状态（0正常1停用）';
alter table SYS_PARA
  add constraint PK_SYS_PARA_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
prompt Creating SYS_GROUP...
create table SYS_GROUP
(
  ID          NUMBER(20) not null,
  DESCRIPTION VARCHAR2(255),
  NAME        VARCHAR2(255),
  PARENT_ID   NUMBER(20),
  AREA_CODE   VARCHAR2(16),
  STREET_CODE VARCHAR2(16)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_GROUP
  is '系统-组织机构表';
comment on column SYS_GROUP.ID
  is '主键id';
comment on column SYS_GROUP.DESCRIPTION
  is '描述';
comment on column SYS_GROUP.NAME
  is '组织机构名称';
comment on column SYS_GROUP.PARENT_ID
  is '父级id';
comment on column SYS_GROUP.AREA_CODE
  is '所属城区';
comment on column SYS_GROUP.STREET_CODE
  is '所属街道';
alter table SYS_GROUP
  add constraint PK_SYS_GROUP_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_GROUP
  add constraint FK_SYS_GROUP_PARENT_ID foreign key (PARENT_ID)
  references SYS_GROUP (ID);

prompt Creating SYS_LOG...
create table SYS_LOG
(
  ID          NUMBER(20) not null,
  CREATE_TIME DATE,
  IP_ADDRESS  VARCHAR2(16),
  LOG_LEVEL   VARCHAR2(16),
  MESSAGE     VARCHAR2(2000),
  USERNAME    VARCHAR2(32)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_LOG
  is '系统：日志记录表';
comment on column SYS_LOG.ID
  is '主键id';
comment on column SYS_LOG.CREATE_TIME
  is '记录时间';
comment on column SYS_LOG.IP_ADDRESS
  is 'ip地址';
comment on column SYS_LOG.LOG_LEVEL
  is '日志级别';
comment on column SYS_LOG.MESSAGE
  is '日志内容';
comment on column SYS_LOG.USERNAME
  is '操作用户';
alter table SYS_LOG
  add constraint PK_SYS_LOG_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating SYS_MODULE...
create table SYS_MODULE
(
  ID          NUMBER(20) not null,
  DESCRIPTION VARCHAR2(255),
  NAME        VARCHAR2(32),
  PRIORITY    NUMBER(10),
  URL         VARCHAR2(255),
  PARENT_ID   NUMBER(20),
  TYPE        CHAR(1),
  ACTIONS     VARCHAR2(32),
  METHODS     VARCHAR2(32)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_MODULE
  is '系统-功能模块权限表';
comment on column SYS_MODULE.ID
  is '主键id';
comment on column SYS_MODULE.DESCRIPTION
  is '描述';
comment on column SYS_MODULE.NAME
  is '模块名称';
comment on column SYS_MODULE.PRIORITY
  is '优先级';
comment on column SYS_MODULE.URL
  is '请求地址';
comment on column SYS_MODULE.PARENT_ID
  is '父级id';
comment on column SYS_MODULE.TYPE
  is '菜单类型：1、显示菜单；0、操作动作';
comment on column SYS_MODULE.ACTIONS
  is '功能操作名称';
comment on column SYS_MODULE.METHODS
  is '功能方法名称';
alter table SYS_MODULE
  add constraint PK_SYS_MODULE_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_MODULE
  add constraint FK_SYS_MODULE_PARENTID foreign key (PARENT_ID)
  references SYS_MODULE (ID);

prompt Creating SYS_ROLE...
create table SYS_ROLE
(
  ID          NUMBER(20) not null,
  DESCRIPTION VARCHAR2(255),
  NAME        VARCHAR2(64)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_ROLE
  is '系统-角色表';
comment on column SYS_ROLE.ID
  is '主键id';
comment on column SYS_ROLE.DESCRIPTION
  is '角色描述';
comment on column SYS_ROLE.NAME
  is '角色名称';
alter table SYS_ROLE
  add constraint PK_SYS_ROLE_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_ROLE
  add constraint UI_SYS_ROLE_NAME unique (NAME)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );

prompt Creating SYS_ROLE_MODULE...
create table SYS_ROLE_MODULE
(
  ID        NUMBER(20) not null,
  ROLE_ID   NUMBER(20),
  MODULE_ID NUMBER(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_ROLE_MODULE
  is '系统-角色模块权限关联表';
comment on column SYS_ROLE_MODULE.ID
  is '主键id';
comment on column SYS_ROLE_MODULE.ROLE_ID
  is '角色id';
comment on column SYS_ROLE_MODULE.MODULE_ID
  is '模块id';
alter table SYS_ROLE_MODULE
  add constraint PK_SYS_ROLE_MODULE_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_ROLE_MODULE
  add constraint FK_SYS_ROLE_MODULE_MODULEID foreign key (MODULE_ID)
  references SYS_MODULE (ID);
alter table SYS_ROLE_MODULE
  add constraint FK_SYS_ROLE_MODULE_ROLEID foreign key (ROLE_ID)
  references SYS_ROLE (ID);

prompt Creating SYS_USER...
create table SYS_USER
(
  ID          NUMBER(20) not null,
  REALNAME    VARCHAR2(64),
  USERNAME    VARCHAR2(32),
  PASSWORD    VARCHAR2(64),
  SALT        VARCHAR2(32),
  CREATE_TIME DATE,
  STATUS      VARCHAR2(20),
  EMAIL       VARCHAR2(128),
  PHONE       VARCHAR2(32),
  GROUP_ID    NUMBER(20)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_USER
  is '系统-用户表';
comment on column SYS_USER.ID
  is '主键id';
comment on column SYS_USER.REALNAME
  is '真实姓名';
comment on column SYS_USER.USERNAME
  is '登录用户名';
comment on column SYS_USER.PASSWORD
  is 'MD5(用户密码+随机码)';
comment on column SYS_USER.SALT
  is '随机码';
comment on column SYS_USER.CREATE_TIME
  is '创建时间';
comment on column SYS_USER.STATUS
  is '激活状态';
comment on column SYS_USER.EMAIL
  is '邮箱';
comment on column SYS_USER.PHONE
  is '电话';
comment on column SYS_USER.GROUP_ID
  is '所属机构id';
alter table SYS_USER
  add constraint PK_SYS_USER_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_USER
  add constraint UI_SYS_USER_NAME unique (USERNAME)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_USER
  add constraint FK_SYS_USER_GOURPID foreign key (GROUP_ID)
  references SYS_GROUP (ID);

prompt Creating SYS_USER_ROLE...
create table SYS_USER_ROLE
(
  ID       NUMBER(20) not null,
  ROLE_ID  NUMBER(20),
  USER_ID  NUMBER(20),
  PRIORITY NUMBER(10)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
comment on table SYS_USER_ROLE
  is '系统-用户和角色关联表';
comment on column SYS_USER_ROLE.ID
  is '主键id';
comment on column SYS_USER_ROLE.ROLE_ID
  is '角色id';
comment on column SYS_USER_ROLE.USER_ID
  is '用户id';
comment on column SYS_USER_ROLE.PRIORITY
  is '优先级';
alter table SYS_USER_ROLE
  add constraint PK_SYS_USER_ROLE_ID primary key (ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
alter table SYS_USER_ROLE
  add constraint FK_SYS_USER_ROLE_ROLEID foreign key (ROLE_ID)
  references SYS_ROLE (ID);
alter table SYS_USER_ROLE
  add constraint FK_SYS_USER_ROLE_USERID foreign key (USER_ID)
  references SYS_USER (ID);

prompt Disabling triggers for SYS_CODE...
alter table SYS_CODE disable all triggers;
prompt Disabling triggers for SYS_GROUP...
alter table SYS_GROUP disable all triggers;
prompt Disabling triggers for SYS_LOG...
alter table SYS_LOG disable all triggers;
prompt Disabling triggers for SYS_MODULE...
alter table SYS_MODULE disable all triggers;
prompt Disabling triggers for SYS_ROLE...
alter table SYS_ROLE disable all triggers;
prompt Disabling triggers for SYS_ROLE_MODULE...
alter table SYS_ROLE_MODULE disable all triggers;
prompt Disabling triggers for SYS_USER...
alter table SYS_USER disable all triggers;
prompt Disabling triggers for SYS_USER_ROLE...
alter table SYS_USER_ROLE disable all triggers;
prompt Disabling foreign key constraints for SYS_GROUP...
alter table SYS_GROUP disable constraint FK_SYS_GROUP_PARENT_ID;
prompt Disabling foreign key constraints for SYS_MODULE...
alter table SYS_MODULE disable constraint FK_SYS_MODULE_PARENTID;
prompt Disabling foreign key constraints for SYS_ROLE_MODULE...
alter table SYS_ROLE_MODULE disable constraint FK_SYS_ROLE_MODULE_MODULEID;
alter table SYS_ROLE_MODULE disable constraint FK_SYS_ROLE_MODULE_ROLEID;
prompt Disabling foreign key constraints for SYS_USER...
alter table SYS_USER disable constraint FK_SYS_USER_GOURPID;
prompt Disabling foreign key constraints for SYS_USER_ROLE...
alter table SYS_USER_ROLE disable constraint FK_SYS_USER_ROLE_ROLEID;
alter table SYS_USER_ROLE disable constraint FK_SYS_USER_ROLE_USERID;
prompt Deleting SYS_USER_ROLE...
delete from SYS_USER_ROLE;
commit;
prompt Deleting SYS_USER...
delete from SYS_USER;
commit;
prompt Deleting SYS_ROLE_MODULE...
delete from SYS_ROLE_MODULE;
commit;
prompt Deleting SYS_ROLE...
delete from SYS_ROLE;
commit;
prompt Deleting SYS_MODULE...
delete from SYS_MODULE;
commit;
prompt Deleting SYS_LOG...
delete from SYS_LOG;
commit;
prompt Deleting SYS_GROUP...
delete from SYS_GROUP;
commit;
prompt Deleting SYS_CODE...
delete from SYS_CODE;
commit;
prompt Loading SYS_CODE...
insert into sys_code (ID, CODE_TYPE, TYPE_NAME, CODE_VALUE, CODE_NAME, CODE_DESC, CODE_STATE, ORD_NO)
values (1, 'MODULE_TYPE', '菜单类型', '1', '显示菜单', '', '0', 1);
insert into sys_code (ID, CODE_TYPE, TYPE_NAME, CODE_VALUE, CODE_NAME, CODE_DESC, CODE_STATE, ORD_NO)
values (2, 'MODULE_TYPE', '菜单类型', '0', '操作动作', '', '0', 0);
insert into sys_code (ID, CODE_TYPE, TYPE_NAME, CODE_VALUE, CODE_NAME, CODE_DESC, CODE_STATE, ORD_NO)
values (3, 'SEX', '性别', '1', '男', '', '0', 1);
insert into sys_code (ID, CODE_TYPE, TYPE_NAME, CODE_VALUE, CODE_NAME, CODE_DESC, CODE_STATE, ORD_NO)
values (4, 'SEX', '性别', '0', '女', '', '0', 0);
insert into sys_code (ID, CODE_TYPE, TYPE_NAME, CODE_VALUE, CODE_NAME, CODE_DESC, CODE_STATE, ORD_NO)
values (5, 'STATE', '状态', '1', '停用', '', '0', 1);
insert into sys_code (ID, CODE_TYPE, TYPE_NAME, CODE_VALUE, CODE_NAME, CODE_DESC, CODE_STATE, ORD_NO)
values (6, 'STATE', '状态', '0', '正常', '', '0', 0);
insert into sys_code (ID, CODE_TYPE, TYPE_NAME, CODE_VALUE, CODE_NAME, CODE_DESC, CODE_STATE, ORD_NO)
values (7, 'STATUS', '用户状态', 'enabled', '可用', '', '0', 0);
insert into sys_code (ID, CODE_TYPE, TYPE_NAME, CODE_VALUE, CODE_NAME, CODE_DESC, CODE_STATE, ORD_NO)
values (8, 'STATUS', '用户状态', 'disabled', '不可用', '', '0', 1);
insert into sys_code (ID, CODE_TYPE, TYPE_NAME, CODE_VALUE, CODE_NAME, CODE_DESC, CODE_STATE, ORD_NO)
values (9, 'AREA_LEVEL', '区域级别', '1', '城区', '', '0', 1);
insert into sys_code (ID, CODE_TYPE, TYPE_NAME, CODE_VALUE, CODE_NAME, CODE_DESC, CODE_STATE, ORD_NO)
values (10, 'AREA_LEVEL', '区域级别', '2', '街道', '', '0', 2);
commit;
prompt Loading SYS_GROUP...
insert into SYS_GROUP (ID, DESCRIPTION, NAME, PARENT_ID,AREA_CODE,STREET_CODE)
values (1, '不能删除，虚拟组织，提供根节点', '根组织', null,'','');
insert into SYS_GROUP (ID, DESCRIPTION, NAME, PARENT_ID,AREA_CODE,STREET_CODE)
values (2, null, '停车收费监管中心', 1,'','');
insert into SYS_GROUP (ID, DESCRIPTION, NAME, PARENT_ID,AREA_CODE,STREET_CODE)
values (3, null, '东湖区管理站', 2,'01','');
insert into SYS_GROUP (ID, DESCRIPTION, NAME, PARENT_ID,AREA_CODE,STREET_CODE)
values (4, null, '西湖区管理站', 2,'01','');
commit;
prompt 4 records loaded
prompt Loading SYS_LOG...
prompt Table is empty
prompt Loading SYS_MODULE...
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (28, null, '系统日志', 2, '/system/config/log/list', 8, '1', 'Log', 'view');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (29, null, '删除日志', 99, '/system/config/log/delete', 28, '0', 'Log', 'delete');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (26, null, '编辑代码', 99, '/system/config/code/update', 24, '0', 'Code', 'edit');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (27, null, '删除代码', 99, '/system/config/code/delete', 24, '0', 'Code', 'delete');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (24, null, '数据字典', 1, '/system/config/code/list', 8, '1', 'Code', 'view');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (25, null, '添加代码', 99, '/system/config/code/create', 24, '0', 'Code', 'save');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (1, '所有模块的根节点，不能删除', '根模块', 1, '#', null, '1', null, null);
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (2, '安全管理:包含权限管理、模块管理、组织管理、角色管理', '安全管理', 1, '#', 23, '1', 'System', 'Security');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (3, null, '用户管理', 1, '/system/security/user/list', 2, '1', 'User', 'view');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (4, null, '角色管理', 2, '/system/security/role/list', 2, '1', 'Role', 'view');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (5, null, '模块管理', 3, '/system/security/module/tree', 2, '1', 'Module', 'view');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (8, '查询平台的一些基本信息配置。', '系统设置', 2, '#', 23, '1', 'System', 'Config');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (7, null, '组织管理', 4, '/system/security/group/tree', 2, '1', 'Group', 'view');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (9, null, '缓存管理', 99, '/system/security/cacheManage/index', 2, '1', 'CacheManage', 'view');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (10, null, '添加用户', 99, '/system/security/user/create', 3, '0', 'User', 'save');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (11, null, '编辑用户', 99, '/system/security/user/update', 3, '0', 'User', 'edit');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (12, null, '删除用户', 99, '/system/security/user/delete', 3, '0', 'User', 'delete');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (13, null, '添加角色', 99, '/system/security/role/create', 4, '0', 'Role', 'save');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (14, null, '删除角色', 99, '/system/security/role/delete', 4, '0', 'Role', 'delete');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (15, null, '编辑角色', 99, '/system/security/role/update', 4, '0', 'Role', 'edit');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (16, null, '添加组织', 99, '/system/security/group/create', 7, '0', 'Group', 'save');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (17, null, '编辑组织', 99, '/system/security/group/update', 7, '0', 'Group', 'edit');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (18, null, '删除组织', 99, '/system/security/group/delete', 7, '0', 'Group', 'delete');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (19, null, '添加模块', 99, '/system/security/module/create', 5, '0', 'Module', 'save');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (20, null, '编辑模块', 99, '/system/security/module/update', 5, '0', 'Module', 'edit');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (21, null, '删除模块', 99, '/system/security/module/delete', 5, '0', 'Module', 'delete');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (22, null, '清理缓存', 99, '/system/security/cacheManage/update', 9, '0', 'CacheManage', 'edit');
insert into SYS_MODULE (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (23, null, '系统管理', 1, '#', 1, '1', 'Index', 'sysmgr');
insert into sys_module (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (30, '', '系统参数', 3, '/system/config/para/list', 8, '1', 'Para', 'view');
insert into sys_module (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (31, '', '添加参数', 99, '/system/config/para/create', 30, '0', 'Para', 'save');
insert into sys_module (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (32, '', '编辑参数', 99, '/system/config/para/update', 30, '0', 'Para', 'edit');
insert into sys_module (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (33, '', '删除参数', 99, '/system/config/para/delete', 30, '0', 'Para', 'delete');
insert into sys_module (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (34, '', '行政区划管理', 99, '/system/config/area/tree', 8, '1', 'Area', 'view');
insert into sys_module (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (35, '', '添加行政区划', 99, '/system/config/area/create', 34, '0', 'Area', 'save');
insert into sys_module (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (36, '', '修改行政区划', 99, '/system/config/area/update', 34, '0', 'Area', 'edit');
insert into sys_module (ID, DESCRIPTION, NAME, PRIORITY, URL, PARENT_ID, TYPE, ACTIONS, METHODS)
values (37, '', '删除行政区划', 99, '/system/config/area/delete', 34, '0', 'Area', 'delete');

commit;
prompt 32 records loaded
prompt Loading SYS_ROLE...
insert into SYS_ROLE (ID, DESCRIPTION, NAME)
values (1, null, '超级管理员');
commit;
prompt 1 records loaded
prompt Loading SYS_ROLE_MODULE...
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (26, 1, 28);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (27, 1, 29);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (24, 1, 26);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (25, 1, 27);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (1, 1, 2);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (2, 1, 3);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (3, 1, 4);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (4, 1, 5);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (5, 1, 7);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (6, 1, 8);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (7, 1, 9);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (8, 1, 10);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (9, 1, 11);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (10, 1, 12);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (11, 1, 13);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (12, 1, 14);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (13, 1, 15);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (14, 1, 16);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (15, 1, 17);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (16, 1, 18);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (17, 1, 19);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (18, 1, 20);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (19, 1, 21);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (20, 1, 22);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (21, 1, 23);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (23, 1, 25);
insert into SYS_ROLE_MODULE (ID, ROLE_ID, MODULE_ID)
values (22, 1, 24);
insert into sys_role_module (ID, ROLE_ID, MODULE_ID)
values (28, 1, 30);
insert into sys_role_module (ID, ROLE_ID, MODULE_ID)
values (29, 1, 31);
insert into sys_role_module (ID, ROLE_ID, MODULE_ID)
values (30, 1, 32);
insert into sys_role_module (ID, ROLE_ID, MODULE_ID)
values (31, 1, 33);
insert into sys_role_module (ID, ROLE_ID, MODULE_ID)
values (32, 1, 34);
insert into sys_role_module (ID, ROLE_ID, MODULE_ID)
values (33, 1, 35);
insert into sys_role_module (ID, ROLE_ID, MODULE_ID)
values (34, 1, 36);
insert into sys_role_module (ID, ROLE_ID, MODULE_ID)
values (35, 1, 37);
commit;
prompt 27 records loaded
prompt Loading SYS_USER...
insert into SYS_USER (ID, REALNAME, USERNAME, PASSWORD, SALT, CREATE_TIME, STATUS, EMAIL, PHONE, GROUP_ID)
values (1, '超级管理员', 'admin', 'cbe5c339a2beb940bddd43f30486473fac2e0658', '9bdab4aa2895266b', to_date('17-05-2013 12:00:01', 'dd-mm-yyyy hh24:mi:ss'), 'enabled', 'wangj@insigmacc.com', '13988888888', 2);
commit;
prompt 1 records loaded
prompt Loading SYS_USER_ROLE...
insert into SYS_USER_ROLE (ID, ROLE_ID, USER_ID, PRIORITY)
values (1, 1, 1, 1);
commit;
prompt 1 records loaded
prompt Enabling foreign key constraints for SYS_GROUP...
alter table SYS_GROUP enable constraint FK_SYS_GROUP_PARENT_ID;
prompt Enabling foreign key constraints for SYS_MODULE...
alter table SYS_MODULE enable constraint FK_SYS_MODULE_PARENTID;
prompt Enabling foreign key constraints for SYS_ROLE_MODULE...
alter table SYS_ROLE_MODULE enable constraint FK_SYS_ROLE_MODULE_MODULEID;
alter table SYS_ROLE_MODULE enable constraint FK_SYS_ROLE_MODULE_ROLEID;
prompt Enabling foreign key constraints for SYS_USER...
alter table SYS_USER enable constraint FK_SYS_USER_GOURPID;
prompt Enabling foreign key constraints for SYS_USER_ROLE...
alter table SYS_USER_ROLE enable constraint FK_SYS_USER_ROLE_ROLEID;
alter table SYS_USER_ROLE enable constraint FK_SYS_USER_ROLE_USERID;
prompt Enabling triggers for SYS_CODE...
alter table SYS_CODE enable all triggers;
prompt Enabling triggers for SYS_GROUP...
alter table SYS_GROUP enable all triggers;
prompt Enabling triggers for SYS_LOG...
alter table SYS_LOG enable all triggers;
prompt Enabling triggers for SYS_MODULE...
alter table SYS_MODULE enable all triggers;
prompt Enabling triggers for SYS_ROLE...
alter table SYS_ROLE enable all triggers;
prompt Enabling triggers for SYS_ROLE_MODULE...
alter table SYS_ROLE_MODULE enable all triggers;
prompt Enabling triggers for SYS_USER...
alter table SYS_USER enable all triggers;
prompt Enabling triggers for SYS_USER_ROLE...
alter table SYS_USER_ROLE enable all triggers;
set feedback on
set define on
prompt Done.
