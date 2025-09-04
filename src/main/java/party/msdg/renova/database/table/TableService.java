package party.msdg.renova.database.table;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.msdg.renova.base.Codes;
import party.msdg.renova.base.work.WorkAssert;
import party.msdg.renova.base.work.WorkCode;
import party.msdg.renova.database.column.Column;
import party.msdg.renova.database.column.ColumnService;

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

    @Autowired
    private ColumnService columnService;

    /**
     * 批量添加表信息
     * @param sourceId  数据源id
     * @param tables    表信息清单
     */
    public void batchAdd(int sourceId, List<Table> tables) {
        // 0代表系统本身
        tableDao.batchInsert(sourceId, Codes.SYS_CUSER, tables);
    }

    /**
     * 获取表信息,包括字段信息，字段生成配置
     */
    public Table one(int sourceId, String tableName) {
        Table table = tableDao.oneByName(sourceId, tableName);
        WorkAssert.notNull(table).just(WorkCode.DB_TABLE_NOT_FOUND);

        List<Column> columns = columnService.tableColumns(table);
        table.setColumns(columns);
        return table;
    }
}
