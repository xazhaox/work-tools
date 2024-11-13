create database `work-tools`;

use `work-tools`;

CREATE TABLE `sys_users`
(
    `id`                bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID（自增主键）',
    `nick_name`         varchar(255) DEFAULT NULL COMMENT '用户昵称',
    `nick_code`         varchar(255) DEFAULT NULL COMMENT '用户代码',
    `password_hash`     varchar(255) DEFAULT NULL COMMENT '密码哈希',
    `salt`              varchar(255) DEFAULT NULL COMMENT '盐值，配合密码使用',
    `telephone`         varchar(20)  DEFAULT NULL COMMENT '手机号码',
    `email`             varchar(128) DEFAULT NULL COMMENT '邮件地址',
    `profile_photo_url` varchar(255) DEFAULT NULL COMMENT '用户头像URL',
    `certification`     tinyint(1)   DEFAULT NULL COMMENT '实名认证状态（TRUE或FALSE）',
    `real_name`         varchar(255) DEFAULT NULL COMMENT '真实姓名',
    `id_card_no`        varchar(255) DEFAULT NULL COMMENT '身份证号',
    `user_role`         varchar(128) DEFAULT NULL COMMENT '用户角色',
    `place_org`         varchar(128) DEFAULT NULL COMMENT '所属机构',
    `place_dept`        varchar(128) DEFAULT NULL COMMENT '所属部门',
    `duty_block`        varchar(128) DEFAULT NULL COMMENT '用户可操做模块',
    `state`             varchar(64)  DEFAULT NULL COMMENT '用户状态（ACTIVE：活动表示启用，FROZEN：冻结表示停用）',
    `deleted`           int          DEFAULT NULL COMMENT '是否逻辑删除，0为未删除，非0为已删除',
    `last_login_time`   datetime     DEFAULT NULL COMMENT '最后登录时间',
    `lock_version`      int          DEFAULT NULL COMMENT '乐观锁版本号',
    `gmt_create`        datetime        NOT NULL COMMENT '创建时间',
    `gmt_modified`      datetime        NOT NULL COMMENT '最后更新时间',
    `temp`              varchar(255) DEFAULT NULL COMMENT '备用字段',
    PRIMARY KEY (`id`)
) COMMENT = '用户信息表';

CREATE TABLE `sys_operation_log`
(
    `log_id`             bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '日志主键',
    `title`              varchar(50)     NULL DEFAULT NULL COMMENT '模块标题',
    `business_type`      decimal(11, 0)  NULL DEFAULT 0 COMMENT '业务类型（0其它 1新增 2修改 3删除）',
    `method`             varchar(255)    NULL DEFAULT NULL COMMENT '方法名称',
    `request_method`     varchar(10)     NULL DEFAULT NULL COMMENT '请求方式',
    `operator_type`      decimal(11, 0)  NULL DEFAULT 0 COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
    `operation_name`     varchar(50)     NULL DEFAULT NULL COMMENT '操作人员',
    `dept_name`          varchar(50)     NULL DEFAULT NULL COMMENT '部门名称',
    `operation_url`      varchar(255)    NULL DEFAULT NULL COMMENT '请求URL',
    `operation_ip`       varchar(128)    NULL DEFAULT NULL COMMENT '主机地址',
    `operation_location` varchar(255)    NULL DEFAULT NULL COMMENT '操作地点',
    `operation_param`    longtext        NULL COMMENT '请求参数',
    `json_result`        longtext        NULL COMMENT '返回参数',
    `status`             decimal(11, 0)  NULL DEFAULT 0 COMMENT '操作状态（0正常 1异常）',
    `error_msg`          longtext        NULL COMMENT '错误消息',
    `operation_time`     datetime        NULL DEFAULT NULL COMMENT '操作时间（创建时间）',
    PRIMARY KEY (`log_id`) USING BTREE
) COMMENT = '操作日志记录';

CREATE TABLE `sys_login_log`
(
    `login_id`       bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_code`      varchar(128)    NULL DEFAULT NULL COMMENT '用户编号',
    `user_name`      varchar(255)    NULL DEFAULT NULL COMMENT '用户名称',
    `status`         varchar(50)     NULL DEFAULT NULL COMMENT '登录状态 登录状态 SUCCESS 成功 FAIL 失败',
    `ip_address`     varchar(128)    NULL DEFAULT NULL COMMENT '登录IP地址',
    `login_location` varchar(255)    NULL DEFAULT NULL COMMENT '登录地点',
    `browser`        varchar(64)     NULL DEFAULT NULL COMMENT '浏览器类型',
    `os`             varchar(64)     NULL DEFAULT NULL COMMENT '操作系统',
    `msg`            varchar(255)    NULL DEFAULT NULL COMMENT '提示消息',
    `engine`         varchar(10)     NULL DEFAULT NULL COMMENT '引擎类型',
    `mobile`         varchar(2)      NULL DEFAULT NULL COMMENT '是否为移动平台',
    `platform`       varchar(255)    NULL DEFAULT NULL COMMENT '平台类型',
    `login_time`     datetime        NULL DEFAULT NULL COMMENT '访问时间（创建时间）',
    `remark`         varchar(255)    NULL DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (`login_id`) USING BTREE
) COMMENT = '登录日志表';