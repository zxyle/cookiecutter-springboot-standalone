<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${table.packageName}.${table.className}Mapper">

    <select id="page" resultType="${package}.${table.className}">
      SELECT
        id,
        create_time,
        update_time,
      <#list table.columns as column>
        ${column.name}<#if column?is_last><#else>,</#if>
      </#list>
      FROM
        ${table.tableName}
    </select>

    <select id="list" resultType="${package}.${table.className}">
      SELECT
        id,
        create_time,
        update_time,
      <#list table.columns as column>
        ${column.name}<#if column?is_last><#else>,</#if>
      </#list>
      FROM
        ${table.tableName}
      <where>
        <if test="req.ids != null and req.ids.size() > 0">
        AND id IN
        <foreach collection="req.ids" item="id" open="(" separator="," close=")">
        </foreach>
        </if>
      </where>
    </select>
</mapper>
