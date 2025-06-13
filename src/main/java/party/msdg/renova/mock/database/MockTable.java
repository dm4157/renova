package party.msdg.renova.mock.database;

import lombok.Data;

import java.util.Date;

@Data
public class MockTable {
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

    private int cuser;
    private Date ctime;
    private int muser;
    private Date mtime;
}
