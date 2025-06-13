package party.msdg.renova.mock.value;

import lombok.Data;

import java.util.Date;

/**
 * 字段生成配置实体
 *
 * @author   Mr.Ten
 * @summary  字段生成配置实体
 * @since 2025/3/25 13:24
 */
@Data
public class ValueConfig {

    /**
     * 主键ID
     */
    private int id;

    /**
     * 字段ID，可能是表或者接口字段
     */
    private int paramId;

    /**
     * 类型，1-表字段，2-接口字段
     */
    private int paramType;

    /**
     * 字段归属的表或接口ID
     */
    private int paramRoot;

    /**
     * 构造类型。1-生成器构造， 2-数据集选取
     */
    private int genType;

    /**
     * 生成器名称
     */
    private String generator;

    /**
     * 生成器配置
     */
    private String genArgs;

    /**
     * 数据集选取方式。 -1-顺序，依次选取； -2-随机； 自然数-指定位置
     */
    private int pick;

    /**
     * 数据集ID
     */
    private int dataSet;

    /**
     * 创建时间
     */
    private Date ctime;

    /**
     * 最新修改时间
     */
    private Date mtime;

    /**
     * 创建人
     */
    private int cuser;

    /**
     * 修改人
     */
    private int muser;
}
