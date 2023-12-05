package com.github.mybatis.mapper;

/**
 * 关于返回值，如果是 `Select` 操作可以返回 null，返回值要么是复杂类型（引入要判断是否为 null，可以使用 Optional.ofNullable(XXX).orElse(DEFAULT) ），要么是 Optional；如果是删除可以是简单数据类型
 *
 * 1. 必须是复杂数据类型
 * 2. `Optional`
 */
public class OrmMapper {

    //
    @Select("select count(1) from " + TABLE_NAME)
    Integer getNumTables();
}
