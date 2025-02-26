package party.msdg.renova.database.table;

import lombok.Data;

/**
 * 数据表信息
 *
 * @author   Mr.Ten
 * @summary  数据表信息
 * @since 2025/2/20 09:42
 */
@Data
public class Table {

    /**
     * 自增id
     */
    private int id;

    /**
     * 数据源ID
     */
    private int sourceId;

    /**
     * 表名
     */
    private String name;

    /**
     * 表注释
     */
    private String remark;

}
