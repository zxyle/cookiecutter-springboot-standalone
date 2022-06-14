package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
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

}
