package party.msdg.renova.database.column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.msdg.renova.base.Codes;

import java.util.List;

@Service
public class ColumnService {

    @Autowired
    private ColumnDao columnDao;

    /**
     * 批量新增字段信息
     * @param sourceId  数据源
     * @param columns   字段
     */
    public void batchAdd(int sourceId, List<Column> columns) {
        // 0代表系统本身
        columnDao.batchInsert(sourceId, Codes.SYS_CUSER, columns);
    }
}
