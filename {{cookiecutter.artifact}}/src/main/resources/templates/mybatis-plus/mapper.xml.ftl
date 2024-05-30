<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${table.packageName}.${table.className}Mapper">

    <select id="page" resultType="${package}.${table.className}">
      SELECT
      <#list table.columns as column>
        ${column.name}<#if column?is_last><#else>,</#if>
      </#list>
      FROM
        ${table.tableName}
    </select>
</mapper>
