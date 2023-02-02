create table trade.product
(
    id       bigint unsigned not null comment '主键ID'
        primary key,
    name     varchar(100)    not null comment '名称',
    rate     decimal         not null comment '利率',
    amount   decimal         not null comment '募集金额',
    raised   decimal         not null comment '已募集金额',
    cycle    int unsigned    not null comment '周期',
    end_time datetime        not null comment '产品募集结束时间',
    constraint product_uni_name
        unique (name)
)
    comment '产品表';

