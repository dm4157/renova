package party.msdg.renova.database.table;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 查询数据库内的表
 *
 * @author   msdg
 * @version  v1
 * @summary  数据库结构基础
 * @since 2023/12/1 18:42
 */
public interface TablesMapping {

//    @Select("select\n" +
//            "  o.name,\n" +
//            "  cast(isnull(p.[value],'') as nvarchar(100)) as remark,\n" +
//            "  #{sourceId} as sourceId\n" +
//            "from sysObjects o\n" +
//            "left join sys.extended_properties p on o.id = p.major_id and p.minor_id = 0\n" +
//            "where xtype='U' order by name")
//    List<Table> allTablesForSqlserver(@Param("sourceId") int sourceId);

    @Select("select\n" +
            "  table_name as name,\n" +
            "  table_comment as remark,\n" +
            "  #{sourceId} as sourceId\n" +
            "from information_schema.tables\n" +
            "where table_schema = #{dbName}")
    List<Table> allTablesForMysql(@Param("sourceId") int sourceId, @Param("dbName") String dbName);
}
