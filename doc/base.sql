use work;

drop table if exists t_uc_user;
create table t_uc_user
(
    id       int auto_increment primary key,
    account  varchar(50)                        not null comment '账号',
    password varchar(20)                        null comment '密码MD5',
    ctime    datetime default CURRENT_TIMESTAMP not null,
    mtime    datetime default current_timestamp not null on update current_timestamp,
    unique index idx_account(account)
);


drop table if exists t_db_source;
create table t_db_source
(
    id       int auto_increment primary key,
    name     varchar(50)                        not null comment "数据源名称",
    type     varchar(20)                        null comment '数据库类型',
    host     varchar(100)                       not null comment "地址",
    port     int                                not null comment '端口',
    dbname   varchar(100)                       not null comment '数据库名称',
    user     varchar(50)                        not null comment "请求用户",
    password varchar(50)                        not null comment "密码",
    status   tinyint  default '0'               null comment "启用状态，1-启用，0-停用",
    remark   varchar(500)                       null comment "备注信息",
    ctime    datetime default CURRENT_TIMESTAMP not null,
    mtime    datetime default current_timestamp not null on update current_timestamp,
    cuser    int                                not null comment "创建人",
    muser    int                                not null comment "修改人" default 0
);

