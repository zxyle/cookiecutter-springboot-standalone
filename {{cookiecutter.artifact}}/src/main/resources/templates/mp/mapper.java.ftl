package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * ${table.comment!} Mapper 接口
 */
@Repository
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

    // 截断表
    @Update("TRUNCATE TABLE ${table.name}")
    void truncate();

    // 创建分区表
    @Select("CREATE TABlE IF NOT EXISTS ${table.name}_<#noparse>${suffix}</#noparse> LIKE ${table.name};")
    void cloneTable(String suffix);

    // 删除分区表
    @Select("DROP TABlE IF EXISTS ${table.name}_<#noparse>${suffix}</#noparse> ;")
    void dropTable(String suffix);

}
