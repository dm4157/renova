package party.msdg.renova.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import party.msdg.renova.base.Codes;
import party.msdg.renova.base.work.Work;
import party.msdg.renova.base.work.WorkAssert;
import party.msdg.renova.base.work.WorkCode;
import party.msdg.renova.database.column.Column;
import party.msdg.renova.database.column.ColumnDao;
import party.msdg.renova.database.source.Source;
import party.msdg.renova.database.source.SourceDao;
import party.msdg.renova.database.table.Table;
import party.msdg.renova.database.table.TableDao;

import java.util.List;

@Component
public class GenTaskService {

    @Autowired
    private GenTaskDao genTaskDao;
    @Autowired
    private TableDao tableDao;
    @Autowired
    private SourceDao sourceDao;
    @Autowired
    private ColumnDao columnDao;

    /**
     * 执行任务
     * 1. 获取任务信息，准备执行上下文
     * 2. 执行任务，生成数据
     * 3. 数据入库
     * @param taskId    任务ID
     */
    public void doTask(int taskId) {
        /*
         * 1. 获取任务信息，准备执行上下文
         */

        // 任务信息
        GenTask task = genTaskDao.one(taskId);
        WorkAssert.notNull(task).just(WorkCode.GEN_TASK_NOT_FOUND);

        // 表信息
        Table table = tableDao.one(task.getTableId());
        WorkAssert.notNull(table).just(WorkCode.DB_TABLE_NOT_FOUND);

        // 数据源
        Source source = sourceDao.one(table.getSourceId());
        WorkAssert.notNull(source).just(WorkCode.DB_SOURCE_NOT_FOUND);

        // 列信息
        List<Column> columns = columnDao.tableColumns(table.getSourceId(), table.getName());
        WorkAssert.notEmpty(columns).just(WorkCode.DB_TABLE_COLUMN_EMPTY);

        /*
         * 2. 执行任务，生成数据
         */
        


        /*
         * 3. 数据入库
         */


    }

}
