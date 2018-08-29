-- 数据库初始化脚本

-- 创建数据库
create database seckill;


use seckill;

create table seckill (
  `seckill_id` bigint not null auto_increment comment '商品库存id',
  `name` varchar(120) not null comment '商品名称',
  `number` int not null comment '库存数量',
  `start_time` timestamp not null comment '秒杀开启时间',
  `end_time` timestamp not null comment '秒杀结束时间',
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  primary key (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE =InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET = utf8 comment '秒杀库存表';

-- 初始化数据

insert into seckill(name, number, start_time, end_time)
values ('1000秒杀iphone6',100,'2015-11-01 00:00:00','2018-11-01 00:00:00')


-- 秒杀成功明细表
-- 用户登陆认证相关的信息
create table success_killed (
  `seckill_id` bigint not null comment '秒杀商品ID',
  `user_phone` bigint not null comment '用户手机',
  `state` tinyint not null default -1 comment '状态标识 -1 无效 1 成功 2 已付款，3 发货',
  `create_time` timestamp not null default current_timestamp comment '创建时间',
  primary key (seckill_id,user_phone),
  key idx_create_time(create_time)
)ENGINE =InnoDB DEFAULT CHARSET = utf8 comment '秒杀成功明细表';
