package party.msdg.renova.database.driver;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import party.msdg.renova.database.column.Column;
import party.msdg.renova.database.table.Table;

import java.util.List;

/**
 * 查询数据库内的表
 *
 * @author   msdg
 * @version  v1
 * @summary  数据库结构基础
 * @since 2023/12/1 18:42
 */
public interface LoadMapping {

    /*
    mysql
     */
    @Select("select\n" +
            "  table_name as name,\n" +
            "  table_comment as remark " +
            "  table_rows as rows " +
            "from information_schema.tables\n" +
            "where table_schema = #{dbName}")
    List<Table> allTablesForMysql(@Param("dbName") String dbName);

    @Select("select\n" +
            "  table_name as tableName,\n" +
            "  ordinal_position as sort,\n" +
            "  column_name as name,\n" +
            "  (case when extra = 'auto_increment' then 1 else 0 end) as ide,\n" +
            "  (case when column_key = 'PRI' then 1 else 0 end) as pk,\n" +
            "  data_type as type,\n" +
            "  (case when numeric_precision is not null then numeric_precision\n" +
            "        when character_octet_length is not null then character_octet_length\n" +
            "        when datetime_precision is not null then datetime_precision\n" +
            "        else 0 end) as `bit`,\n" +
            "  (case when numeric_precision is not null then numeric_precision\n" +
            "        when character_maximum_length is not null then character_maximum_length\n" +
            "        when datetime_precision is not null then datetime_precision\n" +
            "        else 0 end) as length,\n" +
            "  numeric_scale as scale,\n" +
            "  (case when is_nullable = 'NO' then 1 else 0 end) as notNull,\n" +
            "  ifnull(column_default, '') as defValue,\n" +
            "  column_comment as remark " +
            "from information_schema.columns\n" +
            "where table_schema = #{dbName}")
    List<Column> allColumnsForMysql(@Param("dbName") String dbName);
}
