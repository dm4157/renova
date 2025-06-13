package party.msdg.renova.database.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.msdg.renova.base.Codes;

import java.util.List;

/**
 * 表信息维护逻辑
 *
 * @author   Mr.Ten
 * @summary  表信息维护
 * @since 2025/2/20 10:28
 */
@Service
public class TableService {

    @Autowired
    private TableDao tableDao;

    /**
     * 批量添加表信息
     * @param sourceId  数据源id
     * @param tables    表信息清单
     */
    public void batchAdd(int sourceId, List<Table> tables) {
        // 0代表系统本身
        tableDao.batchInsert(sourceId, Codes.SYS_CUSER, tables);
    }

    public Table one(int sourceId, String tableName) {
        return null;
    }

    
}
