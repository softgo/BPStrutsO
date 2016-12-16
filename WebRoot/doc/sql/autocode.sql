-- ----------------------------
-- database structure for javabase
-- ----------------------------
drop database if exists javabase;
create database if not exists javabase; 
use javabase;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
drop table if exists sys_users;
create table sys_users (
  sys_user_id int(11) not null AUTO_INCREMENT comment '系统用户的id',
  parent_id int(11) default -1 comment '系统用户父级id',
  sys_user_name varchar(20) default null  comment '系统用户的名字',
  sys_user_pass varchar(100) default null  comment '系统登陆密码',
  sys_user_sex int(2) default 1  comment '用户的性别: 1:男,0:女',
  sys_user_address varchar(100) default null  comment '住址',
  sys_user_phone varchar(50) default null  comment '手机号码',
  sys_user_mail varchar(50) default null  comment '邮箱地址',
  sys_user_qq varchar(30) default null  comment '联系qq',
  register_time timestamp not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '注册时间',
  sys_status int(2) default 1 comment '用户状态;1:启用,0:弃用',
  PRIMARY KEY (sys_user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8 comment '系统用户管理表';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
drop table if exists sys_roles;
create table sys_roles (
  role_id int(11) not null AUTO_INCREMENT comment '角色id',
  parent_id int(11) default -1 comment '角色父级id',
  role_name varchar(50) default null comment '角色名字',
  role_key varchar(50) default null comment '角色key',
  description varchar(200) default null comment '角色介绍',
  role_status int(2) default 1 comment '角色状态;1:启用,0:弃用',
  primary key (role_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8 comment '角色管理表';

-- ----------------------------
-- Table structure for sys_users_roles
-- ----------------------------
drop table if exists sys_users_roles;
create table sys_users_roles (
  sys_user_id bigint(10) not null default 0,
  role_id bigint(10) not null default 0,
  app_id varchar(100) default null comment '应用id',
  channel_id varchar(100) default null comment '频道id',
  shop_id varchar(100) default null comment '商铺id',
  primary key (sys_user_id,role_id)
) ENGINE=InnoDB default CHARSET=utf8 comment '用户和角色管理关联表';

-- ----------------------------
-- Table structure for sys_sources
-- ----------------------------
drop table if exists sys_sources;
create table sys_sources (
  source_id int(11) not null AUTO_INCREMENT comment '资源id',
  source_name varchar(50) not null comment '资源名称',
  parent_id int(11) not null comment '资源父级Id',
  source_key varchar(50) default null comment '资源key,是唯一的访问Key',
  source_type varchar(10) default null comment '资源类型,0:菜单,1:按钮,2:链接',
  source_url varchar(200) not null comment '资源访问的url',
  source_level int(11) default null comment '资源等级,从 1 开始...',
  description varchar(200) default null comment '资源介绍',
  source_status int(2) default 1 comment '资源状态;1:启用,0:弃用',
  is_iframe int(2) default 0 comment '是否是iframe;1:是,0:否',
  primary key (source_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 default CHARSET=utf8 comment '访问资源管理表';

-- ----------------------------
-- Table structure for sys_sources_roles
-- ----------------------------
drop table if exists sys_sources_roles;
create table sys_sources_roles (
  source_id int(11) not null comment '资源id',
  role_id int(11) not null comment '角色id',
  primary key (source_id,role_id)
) ENGINE=InnoDB default CHARSET=utf8 comment '访问资源和角色管理关联表';

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
drop table if exists sys_logs;
create table sys_logs (
  log_id int(11) NOT NULL AUTO_INCREMENT comment 'log ID',
  sys_user_id varchar(30) default null comment '系统用户登陆id',
  sys_user_name varchar(30) default null comment '系统用户登陆名字',
  module varchar(30) default null comment '操作哪个模块',
  action varchar(500) default null comment '操作详述',
  user_ip varchar(30) default null comment '登陆ip',
  operator_time timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
  log_status int(2) default 1 comment '日志状态;1:可见,0:不可见',
  PRIMARY KEY (log_id)
) ENGINE=InnoDB default CHARSET=utf8 comment '系统操作日志表';


-- ----------------------------
-- Table structure for login_logs
-- ----------------------------
drop table if exists login_logs;
create table login_logs (
  login_id int(11) NOT NULL AUTO_INCREMENT comment '登陆id',
  sys_user_id int(11) default null  comment '系统登陆用户id',
  sys_user_name varchar(20) default null comment '系统登陆用户名',
  login_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP comment '登陆时间,默认是当前时间',
  login_ip varchar(40) default null comment '登陆ip',
  login_status int(2) default 1 comment '日志状态;1:可见,0:不可见',
  PRIMARY KEY (login_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '登陆日志记录表';

-- ----------------------------
-- Table structure for sys_server_infos
-- ----------------------------
drop table if exists sys_server_infos;
create table sys_server_infos (
  info_id int(10) NOT NULL AUTO_INCREMENT comment '系统id',
  cpu_usage varchar(255) DEFAULT NULL comment 'cpu内存',
  set_cpu_usage varchar(255) DEFAULT NULL comment '设置cpu内存',
  jvm_usage varchar(255) DEFAULT NULL comment 'jvm内存',
  set_jvm_usage varchar(255) DEFAULT NULL comment '设置jvm内存',
  ram_usage varchar(255) DEFAULT NULL comment '内存使用',
  set_ram_usage varchar(255) DEFAULT NULL comment '设置内存使用',
  manager_email varchar(255) DEFAULT NULL comment '管理的email',
  operator_time timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP comment '操作时间',
  mark varchar(255) DEFAULT NULL comment '系统备注',
  PRIMARY KEY (info_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 comment '系统机器配置';

-- ----------------------------
-- init data for javabase database 
-- ----------------------------

use javabase;

insert into sys_sources values ('1', '用户管理', '1010', 'sys_users', '0', 'sys_users', '1', '用户管理',1,0);
insert into sys_sources values ('2', '用户列表', '1', 'sys_users_find', '1', '/background/sysUsers/find.html', '2', '用户列表',1,0);
insert into sys_sources values ('3', '新增用户', '2', 'sys_users_addUI', '2', '/background/sysUsers/addUI.html', '3', '新增用户',1,0);
insert into sys_sources values ('4', '编辑用户', '2', 'sys_users_edit', '2', '/background/sysUsers/findById.html', '4', '编辑用户',1,0);
insert into sys_sources values ('5', '删除用户', '2', 'sys_users_delete', '2', '/background/sysUsers/deleteById.html', '5', '删除用户',1,0);
insert into sys_sources values ('6', '分配角色', '2', 'sys_users_fenpeirole', '2', 'sys_users_fenpeirole', '6', '分配角色',1,0);
insert into sys_sources values ('7', '详细信息', '2', 'sys_users_info', '2', 'sys_users_info', '7', '显示详细信息',1,0);

insert into sys_sources values ('8', '角色管理', '1010', 'sys_roles', '0', 'sys_roles', '8', '角色管理',1,0);
insert into sys_sources values ('9', '角色列表', '8', 'sys_roles_find', '1', '/background/sysRoles/find.html', '9', '角色列表',1,0);
insert into sys_sources values ('10', '新增角色', '9', 'sys_roles_addUI', '2', '/background/sysRoles/addUI.html', '10', '新增角色',1,0);
insert into sys_sources values ('11', '编辑角色', '9', 'sys_roles_edit', '2', '/background/sysRoles/findById.html', '11', '编辑角色',1,0);
insert into sys_sources values ('12', '删除角色', '9', 'sys_roles_delete', '2', '/background/sysRoles/deleteById.html', '12', '删除角色',1,0);
insert into sys_sources values ('13', '分配权限', '9', 'sys_roles_permissions', '2', 'sys_roles_permissions', '13', '分配权限',1,0);
insert into sys_sources values ('14', '详细信息', '9', 'sys_roles_info', '2', 'sys_roles_info', '14', '显示详细信息',1,0);

insert into sys_sources values ('15', '资源管理', '1010', 'sys_sources', '0', 'sys_sources', '15', '资源管理',1,0);
insert into sys_sources values ('16', '资源列表', '15', 'sys_sources_find', '1', '/background/sysSources/find.html', '16', '资源列表',1,0);
insert into sys_sources values ('17', '添加资源', '15', 'sys_sources_addUI', '1', '/background/sysSources/addUI.html', '17', '添加资源',1,0);
insert into sys_sources values ('18', '编辑资源', '16', 'sys_sources_edit', '2', '/background/sysSources/findById.html', '18', '编辑权限资源',1,0);
insert into sys_sources values ('19', '删除资源', '16', 'sys_sources_delete', '2', '/background/sysSources/deleteById.html', '19', '删除权限资源',1,0);
insert into sys_sources values ('20', '详细信息', '16', 'sys_sources_info', '2', 'sys_sources_info', '20', '显示详细信息',1,0);

insert into sys_sources values ('21', '服务管理', '1010', 'sys_server_infos', '0', 'sys_server_infos', '21', '服务器配置管理',1,0);
insert into sys_sources values ('22', '状态列表', '21', 'sys_server_infos_list', '1', '/background/sysServerInfos/find.html', '22', '状态列表',1,0);
insert into sys_sources values ('23', '预警设置', '21', 'sys_server_infos_warn', '1', '/background/sysServerInfos/forecast.html', '23', '预警设置',1,0);
insert into sys_sources values ('24', '服务器状态', '21', 'sys_server_infos_status', '1', '/background/sysServerInfos/show.html', '24', '服务器状态',1,0);
insert into sys_sources values ('25', '一键生成', '21', 'sys_server_codes', '1', '/background/sysServerInfos/codes.html', '25', '一键生成',1,0);

insert into sys_sources values ('26', '日志管理', '1010', 'sys_manager_logs', '0', 'sys_manager_logs', '26', '日志管理',1,0);
insert into sys_sources values ('27', '操作日志', '26', 'sys_logs_find', '1', '/background/systemLogs/find.html', '27', '操作日志',1,0);
insert into sys_sources values ('28', '登录日志', '26', 'login_logs_list', '1', '/background/systemLogs/logins.html', '28', '登录日志',1,0);

insert into sys_sources_roles values ('1', '1');
insert into sys_sources_roles values ('2', '1');
insert into sys_sources_roles values ('3', '1');
insert into sys_sources_roles values ('4', '1');
insert into sys_sources_roles values ('5', '1');
insert into sys_sources_roles values ('6', '1');
insert into sys_sources_roles values ('7', '1');
insert into sys_sources_roles values ('8', '1');
insert into sys_sources_roles values ('9', '1');
insert into sys_sources_roles values ('10', '1');
insert into sys_sources_roles values ('11', '1');
insert into sys_sources_roles values ('12', '1');
insert into sys_sources_roles values ('13', '1');
insert into sys_sources_roles values ('14', '1');
insert into sys_sources_roles values ('15', '1');
insert into sys_sources_roles values ('16', '1');
insert into sys_sources_roles values ('17', '1');
insert into sys_sources_roles values ('18', '1');
insert into sys_sources_roles values ('19', '1');
insert into sys_sources_roles values ('20', '1');
insert into sys_sources_roles values ('21', '1');
insert into sys_sources_roles values ('22', '1');
insert into sys_sources_roles values ('23', '1');
insert into sys_sources_roles values ('24', '1');
insert into sys_sources_roles values ('25', '1');
insert into sys_sources_roles values ('26', '1');
insert into sys_sources_roles values ('27', '1');
insert into sys_sources_roles values ('28', '1');

insert into sys_roles values ('1',-1,'admin', 'SYS_USER_ROLE', '管理员', '1');
insert into sys_users values ('1',-1,'admin','hoIPI+1VeV3GFzJijYH9cQQzJwLy4WG+',1, '贝吉塔星球', '010-5211110', 'testrocky@163.com', '1577620678', now(),1);  --admin admin
insert into sys_users_roles(sys_user_id,role_id) values ('1', '1');

insert into sys_server_infos values ('1', '9.3', '20', '64.0', '80', '75.0', '80', '1577620678@qq.com', now(), '<font color=\'red\'>内存当前：75.0%,超出预设值  60%</font>');
insert into sys_server_infos values ('2', '0.8', '20', '60.0', '80', '75.0', '80', '1577620678@qq.com', now(), '<font color=\'red\'>内存当前：75.0%,超出预设值  60%</font>');
insert into sys_server_infos values ('3', '1.5', '20', '59.0', '80', '75.0', '80', '1577620678@qq.com', now(), '<font color=\'red\'>内存当前：75.0%,超出预设值  60%</font>');
insert into sys_server_infos values ('4', '0.7', '20', '57.0', '80', '75.0', '80', '1577620678@qq.com', now(), '<font color=\'red\'>内存当前：75.0%,超出预设值  60%</font>');
insert into sys_server_infos values ('5', '2.3', '20', '57.0', '80', '75.0', '80', '1577620678@qq.com', now(), '<font color=\'red\'>内存当前：75.0%,超出预设值  60%</font>');
insert into sys_server_infos values ('6', '17.9', '20', '57.0', '80', '77.0', '80', '1577620678@qq.com',now(), '<font color=\'red\'>内存当前：77.0%,超出预设值  60%</font>');

-- ----------------------------
-- select data for javabase database 
-- ----------------------------

use javabase;

select * from login_logs;
select * from sys_logs;
select * from sys_roles;
select * from sys_server_infos;
select * from sys_sources;
select * from sys_sources_roles;
select * from sys_users;
select * from sys_users_roles;

