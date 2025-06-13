package party.msdg.renova.database.column;

import lombok.Data;
import party.msdg.renova.mock.value.ValueConfig;

/**
 * 数据表字段信息
 *
 * @author   Mr.Ten
 * @summary  表字段
 * @since 2025/2/20 09:54
 */
@Data
public class Column {

    /**
     * 自增id
     */
    private int id;

    /**
     * 字段名
     */
    private String name;

    /**
     * 所属数据源
     */
    private int sourceId;

    /**
     * 所属表名
     */
    private String tableName;

    /**
     * 字段顺序
     */
    private int sort;

    /**
     * 字段类型
     */
    private String type;

    /** 字节数 */
    private int bit;
    /** 长度 */
    private int length;
    /** 小数位数 */
    private int scale;

    /**
     * 是否主键
     */
    private boolean pk;

    /**
     * 是否为空
     */
    private boolean notNull;

    /**
     * 默认值
     */
    private String defValue;

    /**
     * 注释
     */
    private String remark;

    /**
     * 生成器配置
     */
    private ValueConfig valueConfig;
}
