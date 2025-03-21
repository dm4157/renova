package party.msdg.renova.mock;

import lombok.Data;

@Data
public class GenTask {
    private int id;
    private String name;
    private int tableId;

    /**
     * 生成数量
     */
    private int num;

    /**
     * 状态，1-待执行，2-执行中，3-执行完成，4-执行失败
     */
    private int status;
    private String remark;
}
