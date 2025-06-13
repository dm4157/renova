use renova;

drop table if exists t_uc_user;
create table t_uc_user
(
    id       int auto_increment primary key,
    account  varchar(50)                        not null comment '账号',
    password varchar(20)                        null comment '密码MD5',
    ctime    datetime default current_timestamp not null comment '创建时间',
    mtime    datetime default current_timestamp not null on update current_timestamp comment '最新修改时间',
    unique index idx_account (account)
) comment '用户账号';


drop table if exists t_db_source;
create table t_db_source
(
    id       int auto_increment primary key,
    name     varchar(100)                       not null comment '数据源名称',
    type     varchar(20)                        null comment '数据库类型',
    host     varchar(100)                       not null comment '地址',
    port     int                                not null comment '端口',
    dbname   varchar(100)                       not null comment '数据库名称',
    user     varchar(50)                        not null comment '请求用户',
    password varchar(50)                        not null comment '密码',
    status   tinyint  default '0'               null comment '启用状态，1-启用，0-停用',
    remark   varchar(500)                       null comment '备注信息',
    ctime    datetime default current_timestamp not null comment '创建时间',
    mtime    datetime default current_timestamp not null on update current_timestamp comment '最新修改时间',
    cuser    int                                not null comment '创建人',
    muser    int                                not null comment '修改人' default 0
) comment '数据源';

drop table if exists t_db_table;
create table t_db_table
(
    id       int auto_increment primary key,
    name     varchar(200)                       not null comment '数据表名称',
    sourceId int                                not null comment '数据源',
    remark   varchar(500)                       null comment '注释信息',
    ctime    datetime default current_timestamp not null comment '创建时间',
    mtime    datetime default current_timestamp not null on update current_timestamp comment '最新修改时间',
    cuser    int                                not null comment '创建人',
    muser    int                                not null comment '修改人' default 0,
    unique index uidx_table (name, sourceId)
) comment '表信息';

drop table if exists t_db_column;
create table t_db_column
(
    id        int auto_increment primary key,
    name      varchar(100) not null comment '字段名',
    sourceId  int          not null comment '数据源',
    tableName varchar(200) not null comment '表名',
    sort      int          not null                  default 0 comment '排序,正序',
    type      varchar(50)  not null comment '字段类型',
    bit       int          not null comment '字节数',
    length    int          not null comment '长度',
    scale     int          not null comment '小数位数',
    pk        int          not null comment '主键标记',
    notNull   int          not null comment '为空标记',
    defValue  varchar(50)  not null comment '默认值',
    remark    varchar(500) not null comment '注释',
    ctime     datetime                               default current_timestamp not null comment '创建时间',
    mtime     datetime                               default current_timestamp not null on update current_timestamp comment '最新修改时间',
    cuser     int          not null comment '创建人',
    muser     int          not null comment '修改人' default 0,
    unique index uidx_column (name, tableName, sourceId)
) comment '字段信息';

drop table if exists t_mock_value_config;
create table t_mock_value_config
(
    id        int auto_increment primary key,
    paramId   int          not null                  default 0 comment '字段id，可能是表或者接口字段',
    paramType int          not null                  default 1 comment '类型，1-表字段，2-接口字段',
    paramRoot int          not null                  default 0 comment '字段归属的表或接口id',
    genType   int          not null                  default 1 comment '构造类型。1-生成器构造， 2-数据集选取',
    generator varchar(200) not null                  default '' comment '生成器名称',
    genArgs   varchar(200) not null                  default '' comment '生成器配置',
    pick      int          not null                  default -1 comment '数据集选取方式。 -1-顺序，依次选取； -2-随机； 自然数-指定位置',
    dataSet   int          not null                  default 0 comment '数据集id',
    ctime     datetime                               default current_timestamp not null comment '创建时间',
    mtime     datetime                               default current_timestamp not null on update current_timestamp comment '最新修改时间',
    cuser     int          not null comment '创建人',
    muser     int          not null comment '修改人' default 0,
    index idx_param (paramId, paramType)
) comment '字段生成配置';

drop table if exists t_mock_data_set;
create table t_mock_data_set
(
    id       int auto_increment primary key,
    type     int          not null                  default 1 comment '动静类型。1-静态，2-动态',
    data     varchar(100) not null                  default '' comment '静态数据集存储文件名称',
    sourceId int          not null                  default 0 comment '数据源id',
    script   varchar(500) not null                  default '' comment '查询语句',
    ctime    datetime                               default current_timestamp not null comment '创建时间',
    mtime    datetime                               default current_timestamp not null on update current_timestamp comment '最新修改时间',
    cuser    int          not null comment '创建人',
    muser    int          not null comment '修改人' default 0
) comment '数据集-用于数据选取';

drop table if exists t_mock_table;
create table t_mock_table
(
    id      int auto_increment primary key,
    name    varchar(200)                       not null comment '任务名称',
    tableId int                                not null comment '表id',
    num     int                                not null comment '生成数量',
    status  int                                not null comment '状态，1-待执行，2-执行中，3-执行完成，4-执行失败',
    remark  varchar(500)                       null comment '备注信息',
    ctime   datetime default current_timestamp not null comment '创建时间',
    mtime   datetime default current_timestamp not null on update current_timestamp comment '最新修改时间',
    cuser   int                                not null comment '创建人',
    muser   int                                not null comment '修改人' default 0
) comment '表数据生成';