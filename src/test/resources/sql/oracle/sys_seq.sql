---------------------------------------------------
-- Export file for user TCSF                     --
-- Created by Administrator on 2013/6/8, 9:16:33 --
---------------------------------------------------

spool system_object.log

prompt
prompt Creating sequence S_SYS_AREA
prompt ============================
prompt
create sequence TCSF.S_SYS_AREA
minvalue 1
maxvalue 99999999999999999999999
start with 10
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_CODE
prompt ============================
prompt
create sequence TCSF.S_SYS_CODE
minvalue 1
maxvalue 99999999999999999999999
start with 10
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_PARA
prompt ============================
prompt
create sequence TCSF.S_SYS_PARA
minvalue 1
maxvalue 99999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_GROUP
prompt =============================
prompt
create sequence TCSF.S_SYS_GROUP
minvalue 1
maxvalue 99999999999999999999999
start with 5
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_LOG
prompt ===========================
prompt
create sequence TCSF.S_SYS_LOG
minvalue 1
maxvalue 99999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_MODULE
prompt ==============================
prompt
create sequence TCSF.S_SYS_MODULE
minvalue 1
maxvalue 99999999999999999999999
start with 48
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_ROLE
prompt ============================
prompt
create sequence TCSF.S_SYS_ROLE
minvalue 1
maxvalue 99999999999999999999999
start with 2
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_ROLE_MODULE
prompt ===================================
prompt
create sequence TCSF.S_SYS_ROLE_MODULE
minvalue 1
maxvalue 99999999999999999999999
start with 46
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_USER
prompt ============================
prompt
create sequence TCSF.S_SYS_USER
minvalue 1
maxvalue 99999999999999999999999
start with 2
increment by 1
cache 20;

prompt
prompt Creating sequence S_SYS_USER_ROLE
prompt =================================
prompt
create sequence TCSF.S_SYS_USER_ROLE
minvalue 1
maxvalue 99999999999999999999999
start with 2
increment by 1
cache 20;


spool off
